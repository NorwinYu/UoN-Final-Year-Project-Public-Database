package com.mybus.service;


import com.mybus.configuration.ApplicationDataTestConfig;
import com.mybus.configuration.core.CoreAppConfig;
import com.mybus.dao.*;
import com.mybus.model.*;
import com.mybus.util.ServiceUtils;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(classes = { CoreAppConfig.class, ApplicationDataTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration



public class DailyTripManagerTest{
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private OperatorAccountDAO operatorAccountDAO;
    @Autowired
    private StaffDAO staffDAO;
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private DailyTripDAO dailyTripDAO;
    @Autowired
    private DailyTripManager dailyTripManager;
    @Autowired
    private SalaryReportDAO salaryReportDAO;
    @Autowired
    private UserDAO userDAO;

    @Before
    @After
    public void cleanUp(){
        operatorAccountDAO.deleteAll();
        staffDAO.deleteAll();
        vehicleDAO.deleteAll();
        dailyTripDAO.deleteAll();
        salaryReportDAO.deleteAll();
        userDAO.deleteAll();
    }

    @Test
    public void testCreateDailyTrip() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo("AP1234");
        vehicle = vehicleDAO.save(vehicle);
        Set<String> staffIds = new HashSet<>();
        for(int i=0;i<2;i++){
            Staff staff = new Staff();
            staff = staffDAO.save(staff);
            staffIds.add(staff.getId());
        }
        DailyTrip dailyTrip = new DailyTrip();
        dailyTrip.setStaffIds(staffIds);
        dailyTrip.setVehicleId(vehicle.getId());
        dailyTrip.setTripDate(calendar.getTime());
        dailyTrip.setServiceNo("999-k");
        dailyTrip.setDate(ServiceUtils.formatDate(calendar.getTime()));
        dailyTrip = dailyTripManager.addDailyTrip(dailyTrip);
        assertEquals("999-k",dailyTrip.getServiceNo());
        JSONObject query = new JSONObject();
        Page<SalaryReport> salaryReports = dailyTripManager.getSalaryReports(query);
        assertEquals(2,salaryReports.getTotalElements());
    }

    @Test
    public void testGetAllDailyTrips() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-6);
        OperatorAccount operatorAccount = new OperatorAccount();
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo("AP1234");
        vehicle = vehicleDAO.save(vehicle);
        Staff staff = new Staff();
        staff = staffDAO.save(staff);
        Set<String> staffIds = new HashSet<>();
        staffIds.add(staff.getId());
        for(int i=0;i<6;i++){
            DailyTrip dailyTrip = new DailyTrip();
            dailyTrip.setOperatorId(sessionManager.getOperatorId());
            if(i == 2 || i  == 4){
                dailyTrip.setServiceNo("Abc11");
                dailyTrip.setVehicleId(vehicle.getId());
                dailyTrip.setTripDate(calendar.getTime());
                dailyTrip.setStaffIds(staffIds);
            }
            dailyTripDAO.save(dailyTrip);
            calendar.add(Calendar.DATE,1);
        }
        JSONObject query = new JSONObject();
        query.put("size",3);
        query.put("page",0);
        query.put("vehicleId",vehicle.getId());
        query.put("staffId",staff.getId());
        Calendar qCalendar = Calendar.getInstance();
        qCalendar.add(Calendar.DATE,-4);
        query.put("startDate",ServiceUtils.formatDate(qCalendar.getTime()));
        qCalendar.add(Calendar.DATE,1);
        query.put("endDate",ServiceUtils.formatDate(qCalendar.getTime()));
        Page<DailyTrip> page = dailyTripManager.getAllTrips(query);
        List<DailyTrip> trips = page.getContent();
        assertEquals(2,trips.size());
    }

    @Test
    public void testGetDailyTrip(){
        DailyTrip dailyTrip = new DailyTrip();
        dailyTrip.setServiceNo("999-k");
        dailyTrip = dailyTripDAO.save(dailyTrip);
        dailyTripManager.getDailyTrip(dailyTrip.getId());
        assertEquals("999-k",dailyTrip.getServiceNo());
    }

    @Test
    public void testUpdate(){
        DailyTrip dailyTrip = new DailyTrip();
        dailyTrip.setServiceNo("999-k");
        dailyTripDAO.save(dailyTrip);
        dailyTrip.setServiceNo("111-ki");
        dailyTripManager.updateDailyTrip(dailyTrip);
        assertEquals("111-ki",dailyTrip.getServiceNo());
    }

    @Test
    public void testDailyTripsForSalaryReport() throws ParseException {
        User user = new User();
        user.setUserName("kalyani kandula");
        user = userDAO.save(user);
        sessionManager.setCurrentUser(user);
        OperatorAccount operatorAccount = new OperatorAccount();
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);
        Staff s1 = new Staff();
        s1 = staffDAO.save(s1);
        Staff s2 = new Staff();
        s2= staffDAO.save(s2);
        for (int i=0;i<10;i++){
            DailyTrip dailyTrip = new DailyTrip();
            dailyTrip.setOperatorId(sessionManager.getOperatorId());
            dailyTrip.setTripDate(calendar.getTime());
            if(i%2 == 0){
                Set<String> staff = new HashSet<>();
                staff.add(s1.getId());
                dailyTrip.setStaffIds(staff);
            }else{
                Set<String> staff = new HashSet<>();
                staff.add(s2.getId());
                dailyTrip.setStaffIds(staff);
            }
            calendar.add(Calendar.DATE, 1);
            dailyTripDAO.save(dailyTrip);
        }
        Calendar queryCalendar = Calendar.getInstance();
        JSONObject query = new JSONObject();
        query.put("staffId",s2.getId());
        queryCalendar.add(Calendar.DATE,-10);
        query.put("fromDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        queryCalendar.add(Calendar.DATE,10);
        query.put("toDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        Page<DailyTrip> page = dailyTripManager.getAllTrips(query);
        assertEquals(5,page.getTotalElements());
    }

    @Test
    public void testGetSalaryReports() throws ParseException {
        OperatorAccount operatorAccount = new OperatorAccount();
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        Calendar calendar = Calendar.getInstance();
        for(int i=0;i<5;i++){
            SalaryReport salaryReport = new SalaryReport();
            salaryReport.setOperatorId(sessionManager.getOperatorId());
            if(i == 3 || i == 4){
                salaryReport.setPaidOn(calendar.getTime());
            }
            salaryReportDAO.save(salaryReport);
        }
        JSONObject query = new JSONObject();
        Page<SalaryReport> page = dailyTripManager.getSalaryReports(query);
        assertEquals(5,page.getTotalElements());

        query.put("isPaid",true);
        Page<SalaryReport> page1 = dailyTripManager.getSalaryReports(query);
        assertEquals(2,page1.getTotalElements());

    }
   /* @Test
    public void testSalaryReportUpdate(){
        Calendar calendar = Calendar.getInstance();
        Staff staff1 = new Staff();
        staff1.setName("kalyani");
        staff1 = staffDAO.save(staff1);
        Staff staff2 = new Staff();
        staff2.setName("kandula");
        staff2= staffDAO.save(staff2);
        User user = new User();
        user.setUserName("kalyani kandula");
        user.setAmountToBePaid(50000);
        user = userDAO.save(user);
        sessionManager.setCurrentUser(user);
        OperatorAccount operatorAccount = new OperatorAccount();
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        SalaryReport s1 = new SalaryReport();
        s1.setOperatorId(sessionManager.getOperatorId());
        s1.setStaffId(staff1.getId());
        s1.setTripDateString(ServiceUtils.formatDate(calendar.getTime()));
        s1 = salaryReportDAO.save(s1);
        calendar.add(Calendar.DATE,-2);

        SalaryReport s2 = new SalaryReport();
        s2.setOperatorId(sessionManager.getOperatorId());
        s2.setStaffId(staff2.getId());
        s2.setTripDateString(ServiceUtils.formatDate(calendar.getTime()));

        s2 = salaryReportDAO.save(s2);
        Set<String> salaryReportIds = new HashSet<>();
        salaryReportIds.add(s1.getId());
        salaryReportIds.add(s2.getId());
        JSONObject query = new JSONObject();
        query.put("selectedPayments",salaryReportIds);
        query.put("amountPaid",10000);
        boolean value = dailyTripManager.paySalary(query);
        user = sessionManager.getCurrentUser();
        assertEquals(40000,user.getAmountToBePaid(),0);
    }
*/

}