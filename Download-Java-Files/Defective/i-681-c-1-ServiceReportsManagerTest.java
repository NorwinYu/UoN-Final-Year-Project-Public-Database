package com.mybus.service;

import com.mybus.configuration.ApplicationDataTestConfig;
import com.mybus.configuration.core.CoreAppConfig;
import com.mybus.dao.*;
import com.mybus.model.*;
import com.mybus.util.ServiceUtils;
import com.mybus.util.UserTestService;
import org.apache.commons.collections.IteratorUtils;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.*;
/**
 * Created by srinikandula on 2/20/17.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = { CoreAppConfig.class, ApplicationDataTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ServiceReportsManagerTest {

    @Autowired
    private ServiceReportStatusDAO serviceReportStatusDAO;

    @Autowired
    private ServiceReportDAO serviceReportDAO;

    @Autowired
    private ServiceReportsManager serviceReportsManager;

    @Autowired
    private ServiceFormDAO serviceFormDAO;

    @Autowired
    private BranchOfficeDAO branchOfficeDAO;

    @Autowired
    private AgentDAO agentDAO;

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private DueReportManager dueReportManager;

    @Autowired
    private BookingManager bookingManager;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private OperatorAccountDAO operatorAccountDAO;

    @Autowired
    private StaffDAO staffDAO;
    @Before
    @After
    public void cleanup() {
        agentDAO.deleteAll();
        branchOfficeDAO.deleteAll();
        bookingDAO.deleteAll();
        serviceFormDAO.deleteAll();
        serviceReportDAO.deleteAll();
        serviceReportStatusDAO.deleteAll();
        paymentDAO.deleteAll();
        userDAO.deleteAll();
        operatorAccountDAO.deleteAll();
    }
    @Test
    @Ignore
    public void testGetDownloadStatus() throws Exception {
        String date = "2017-02-22";
        ServiceReportStatus serviceReportStatus = new ServiceReportStatus();
        serviceReportStatus.setReportDate(ServiceUtils.parseDate(date, false));
        serviceReportStatus.setOperatorId(null);
        serviceReportStatus.setStatus(ReportDownloadStatus.DOWNLOADED);
        serviceReportStatusDAO.save(serviceReportStatus);
        JSONObject status = serviceReportsManager.getDownloadStatus(date);
        assertTrue(Boolean.valueOf(status.get("downloaded").toString()));
        status = serviceReportsManager.getDownloadStatus("2017-02-02");
        assertFalse(Boolean.valueOf(status.get("downloaded").toString()));
    }

    @Test
    public void testDownloadReport() throws Exception {

    }

    @Test
    public void testUpdateOfficeBalances() throws ParseException {
        OperatorAccount operatorAccount = operatorAccountDAO.save(new OperatorAccount(OperatorAccount.ABHIBUS));

        User user = new User();
        BranchOffice office1 = new BranchOffice();
        office1.setName("Office1");
        office1 = branchOfficeDAO.save(office1);

        BranchOffice office2 = new BranchOffice();
        office2.setName("Office2");
        office2 = branchOfficeDAO.save(office2);

        user.setBranchOfficeId(office2.getId());
        user = userDAO.save(user);
        sessionManager.setCurrentUser(user);
        Agent agent1 = new Agent();
        agent1.setName("TestAgent1");
        agent1.setUsername("TestAgent1");
        agent1.setBranchOfficeId(office1.getId());
        agent1 = agentDAO.save(agent1);

        Agent agent2 = new Agent();
        agent2.setName("TestAgent2");
        agent2.setUsername("TestAgent2");
        agent2.setBranchOfficeId(office2.getId());
        agent2 = agentDAO.save(agent2);
        ServiceReport serviceReport = new ServiceReport();
        serviceReport.setJourneyDate(new Date());
        for(int i=0; i<3; i++) {
            Booking booking = new Booking();
            booking.setBookedBy(agent2.getUsername());
            booking.setSeats("D"+i+",E"+i);
            booking.setNetAmt(2500);
            booking.setPaymentType(BookingType.CASH);
            if(i ==2){
                booking.setDue(true);
            } else {
                serviceReport.setNetCashIncome(serviceReport.getNetCashIncome() + booking.getNetAmt());
            }
            booking.setBookedBy(agent2.getName());
            serviceReport.getBookings().add(bookingDAO.save(booking));
        }
        serviceReport = serviceReportDAO.save(serviceReport);
        serviceReport.setOperatorId(operatorAccount.getId());
        serviceReportsManager.submitReport(serviceReport, ServiceStatus.SUBMITTED.toString());
        List<Booking> bookings = IteratorUtils.toList(bookingDAO.findAll().iterator());
        assertEquals(6, bookings.size());
        user = userDAO.findById(user.getId()).get();
        assertEquals(5000, user.getAmountToBePaid(), 0.0);

        List<Payment> payments = IteratorUtils.toList(paymentDAO.findAll().iterator());
        assertEquals(1, payments.size());
        assertEquals(payments.get(0).getType(), PaymentType.INCOME);
        assertEquals(5000, payments.get(0).getAmount(), 0.0);

        List<BranchOfficeDue> officeDues = dueReportManager.getBranchOfficesDueReports();
        assertEquals(2, officeDues.size());
        officeDues.stream().forEach(office -> {
            if(office.getName().equals("Office2")) {
                assertEquals(2500, office.getTotalDue(),0.0);
            }
        });
        for(Booking booking: bookings){
            if(booking.isDue() && booking.getFormId() != null) {
                boolean paid = bookingManager.payBookingDue(booking.getId());
                assertTrue("payment of the booking failed", paid);
                break;
            }
        }
        officeDues = dueReportManager.getBranchOfficesDueReports();
        assertEquals(2, officeDues.size());
        final User currentUser = userDAO.findById(user.getId()).get();

        officeDues.stream().forEach(office -> {
            if(office.getName().equals("Office2")) {
                assertEquals(0, office.getTotalDue(),0.0);
                assertEquals(0, office.getCashBalance(),0.0);
                assertEquals(7500, currentUser.getAmountToBePaid(), 0.0);
            }
        });
        payments = IteratorUtils.toList(paymentDAO.findAll().iterator());
        assertEquals(2, payments.size());
        payments.stream().forEach(payment -> {
            assertEquals(payment.getType(), PaymentType.INCOME);
        });
    }
    @Ignore
    @Test
    public void testRefreshServiceReport() throws ParseException {
        User user = UserTestService.createNew();
        BranchOffice office = new BranchOffice();
        office = branchOfficeDAO.save(office);
        user.setBranchOfficeId(office.getId());
        user = userDAO.save(user);
        sessionManager.setCurrentUser(user);
        Calendar calendar = Calendar.getInstance();
        List<String> reportIds = new ArrayList<>();
        for(int i=0; i<3; i++) {
            ServiceReport report = new ServiceReport();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            report.setJourneyDate(ServiceUtils.parseDate(ServiceUtils.formatDate(calendar.getTime())));
            report.setServiceNumber("Service"+i);
            report = serviceReportDAO.save(report);
            for(int b=0;b<5;b++) {
                Booking booking = new Booking();
                booking.setServiceReportId(report.getId());
                booking.setNetAmt(100);
                if(b == 3){
                    booking.setDue(true);
                }
                report.getBookings().add(bookingDAO.save(booking));
            }
            //add service expense
            /*
            ServiceExpense serviceExpense = new ServiceExpense(report);
            serviceExpense.setToPayLuggage(100);
            serviceExpense.setPaidLuggage(100);
            serviceExpense.setDriverSalary1(100);
            report.setServiceExpense(serviceExpenseManager.save(serviceExpense));
            */
            serviceReportsManager.submitReport(report, ServiceStatus.SUBMITTED.toString());
            reportIds.add(report.getId());
        }
        user = userDAO.findById(user.getId()).get();
        assertEquals(1200, user.getAmountToBePaid(), 0.0);
        //serviceReportsManager.clearServiceReports(ServiceConstants.parseDate(new Date()), null);
        user = userDAO.findById(user.getId()).get();
        assertEquals(800, user.getAmountToBePaid(), 0.0);
        List<ServiceReport> reports = IteratorUtils.toList(serviceReportDAO.findAll().iterator());
        assertEquals(2, reports.size());
        List<Booking> bookings = IteratorUtils.toList(bookingDAO.findAll().iterator());
        assertEquals(20, bookings.size());
        /*for(ServiceReport report : reports) {
            ServiceReport serviceReport = serviceReportsManager.getReport(report.getId());
            assertNotNull(serviceReport.getServiceExpense());
            assertEquals(100, serviceReport.getServiceExpense().getToPayLuggage(), 0.0);
            assertEquals(100, serviceReport.getServiceExpense().getPaidLuggage(), 0.0);
            assertEquals(100, serviceReport.getServiceExpense().getDriverSalary1(), 0.0);
            assertEquals(0, serviceReport.getServiceExpense().getDriverSalary2(), 0.0);
        }*/

    }


    @Test
    public void testFormSubmit() throws ParseException {
        ServiceReport serviceReport = new ServiceReport();
        serviceReport.setJourneyDate(new Date());
        for(int i=0; i<3; i++) {
            Booking booking = new Booking();
            booking.setSeats("D"+i+",E"+i);
            booking.setNetAmt(2500);
            booking.setBookedBy("test");
            booking.setPaymentType(BookingType.CASH);
            if(i ==2){
                booking.setDue(true);
            } else {
                serviceReport.setNetCashIncome(serviceReport.getNetCashIncome() + booking.getNetAmt());
            }
            serviceReport.getBookings().add(bookingDAO.save(booking));
        }
        serviceReport = serviceReportDAO.save(serviceReport);
        OperatorAccount operatorAccount = operatorAccountDAO.save(new OperatorAccount(OperatorAccount.ABHIBUS));
        User user = userDAO.save(UserTestService.createNew());
        sessionManager.setCurrentUser(user);
        serviceReport.setOperatorId(operatorAccount.getId());
        serviceReportsManager.submitReport(serviceReport, ServiceStatus.SUBMITTED.toString());
        user = userDAO.findById(user.getId()).get();
        assertEquals(5000, user.getAmountToBePaid(), 0.0);
        User verifyingUser = userDAO.save(UserTestService.createNew());
    }


    @Test
    public void testFormVerification() throws ParseException {
        OperatorAccount operatorAccount = operatorAccountDAO.save(new OperatorAccount(OperatorAccount.ABHIBUS));
        ServiceReport serviceReport = new ServiceReport();
        serviceReport.setJourneyDate(new Date());
        for(int i=0; i<3; i++) {
            Booking booking = new Booking();
            booking.setSeats("D"+i+",E"+i);
            booking.setNetAmt(2500);
            booking.setBookedBy("test");
            booking.setPaymentType(BookingType.CASH);
            if(i ==2){
                booking.setDue(true);
            } else {
                serviceReport.setNetCashIncome(serviceReport.getNetCashIncome() + booking.getNetAmt());
            }
            serviceReport.getBookings().add(bookingDAO.save(booking));
        }
        serviceReport = serviceReportDAO.save(serviceReport);
        User user = userDAO.save(UserTestService.createNew());
        serviceReport.setSubmittedBy(user.getId());
        User verifyingUser = userDAO.save(UserTestService.createNew());
        sessionManager.setCurrentUser(verifyingUser);
        serviceReport.setOperatorId(operatorAccount.getId());
        serviceReportsManager.submitReport(serviceReport, ServiceStatus.SUBMITTED.toString());
        user = userDAO.findById(user.getId()).get();
        verifyingUser = userDAO.findById(verifyingUser.getId()).get();
        assertEquals(5000, user.getAmountToBePaid(), 0.0);
        assertEquals(0, verifyingUser.getAmountToBePaid(), 0.0);
    }


    @Test
    public void testFindServiceIncomeReports() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String serviceNumber = "1234";
        for(int i=0; i<10; i++) {
            ServiceReport serviceReport = new ServiceReport();
            serviceReport.setServiceNumber("12345");
            serviceReport.setJourneyDate(calendar.getTime());
            serviceReport.setGrossIncome(100);
            serviceReport.setNetCashIncome(200);
            serviceReportDAO.save(serviceReport);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) +1);
        }
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) -6);
        JSONObject query = new JSONObject();
        query.put("serviceNumber", "12345");
        query.put("startDate", ServiceUtils.formatDate(calendar.getTime()));
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) +5);
        query.put("endDate", ServiceUtils.formatDate(calendar.getTime()));
        List<ServiceReport> reports = serviceReportsManager.findServiceIncomeReports(query);
        assertEquals(5, reports.size());
    }

    @Test
    public void testSalaryReport() throws ParseException {
        OperatorAccount operatorAccount = new OperatorAccount();
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        User user = new User();
        user.setUserName("kalyani");
        user = userDAO.save(user);
        sessionManager.setCurrentUser(user);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -10);
        Staff s1 = new Staff();
        s1 = staffDAO.save(s1);
        Staff s2 = new Staff();
        s2= staffDAO.save(s2);
        for (int i=0;i<10;i++){
            ServiceReport serviceReport = new ServiceReport();
            serviceReport.setOperatorId(sessionManager.getOperatorId());
            serviceReport.setJourneyDate(calendar.getTime());
            if(i%2 == 0){
                Set<String> staff = new HashSet<>();
                staff.add(s1.getId());
                serviceReport.setStaff(staff);
            }else{
                Set<String> staff = new HashSet<>();
                staff.add(s2.getId());
                serviceReport.setStaff(staff);
            }
            calendar.add(Calendar.DATE, 1);
            serviceReport.setStatus(ServiceStatus.SUBMITTED);
            serviceReportDAO.save(serviceReport);
        }
        Calendar queryCalendar = Calendar.getInstance();
        JSONObject query = new JSONObject();
        query.put("staffId",s2.getId());
        queryCalendar.add(Calendar.DATE,-10);
        query.put("fromDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        queryCalendar.add(Calendar.DATE,10);
        query.put("toDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        List<ServiceReport> serviceReports = serviceReportsManager.findSalaryReports(query);
        assertEquals(5,serviceReports.size());

        queryCalendar = Calendar.getInstance();
        query.put("staffId",s1.getId());
        queryCalendar.add(Calendar.DATE,-10);
        query.put("fromDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        queryCalendar.add(Calendar.DATE,1);
        query.put("toDate",ServiceUtils.formatDate(queryCalendar.getTime()));
        serviceReports = serviceReportsManager.findSalaryReports(query);
        assertEquals(2,serviceReports.size());
    }


   @Test
    public void testSaveSalReport() throws ParseException {
       OperatorAccount operatorAccount = new OperatorAccount();
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DATE,-2);
       operatorAccount = operatorAccountDAO.save(operatorAccount);
       sessionManager.setOperatorId(operatorAccount.getId());
       User user = new User();
       user = userDAO.save(user);
       sessionManager.setCurrentUser(user);
       Staff s1 = new Staff();
       s1 = staffDAO.save(s1);
       Staff s2 = new Staff();
       s2= staffDAO.save(s2);
       Set<String> staff1 = new HashSet<>();
       staff1.add(s1.getId());
       ServiceReport serviceReport1 = new ServiceReport();
       serviceReport1.setOperatorId(sessionManager.getOperatorId());
       serviceReport1.setStatus(ServiceStatus.SUBMITTED);
       serviceReport1.setJourneyDate(calendar.getTime());
       serviceReport1.setServiceNumber("121");
       serviceReport1.setStaff(staff1);
       serviceReportDAO.save(serviceReport1);

       calendar.add(Calendar.DATE,1);

       Set<String> staff2 = new HashSet<>();
       staff2.add(s1.getId());
       staff2.add(s2.getId());
       ServiceReport serviceReport2 = new ServiceReport();
       serviceReport2.setOperatorId(sessionManager.getOperatorId());
       serviceReport2.setStatus(ServiceStatus.SUBMITTED);
       serviceReport2.setJourneyDate(calendar.getTime());
       serviceReport2.setServiceNumber("333");
       serviceReport2.setStaff(staff2);
       serviceReportDAO.save(serviceReport2);
       JSONObject q = new JSONObject();
       q.put("staffId",s1.getId());
       List<ServiceReport> serviceReports = serviceReportsManager.findSalaryReports(q);
       assertEquals(2,serviceReports.size());

    }
}