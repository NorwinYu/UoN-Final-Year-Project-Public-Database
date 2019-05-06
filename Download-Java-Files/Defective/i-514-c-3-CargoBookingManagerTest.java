package com.mybus.service;

import com.mybus.configuration.ApplicationDataTestConfig;
import com.mybus.configuration.core.CoreAppConfig;
import com.mybus.dao.*;
import com.mybus.dao.cargo.ShipmentSequenceDAO;
import com.mybus.dto.AllBranchBookingSummary;
import com.mybus.dto.BranchDeliverySummary;
import com.mybus.dto.UserCargoBookingsSummary;
import com.mybus.exception.BadRequestException;
import com.mybus.model.*;
import com.mybus.model.cargo.ShipmentSequence;
import com.mybus.util.CargoBookingTestService;
import com.mybus.util.ServiceUtils;
import org.apache.commons.collections.IteratorUtils;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by srinikandula on 12/10/16.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = { CoreAppConfig.class, ApplicationDataTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CargoBookingManagerTest{

    @Autowired
    private CargoBookingDAO cargoBookingDAO;

    @Autowired
    private CargoBookingManager shipmentManager;

    @Autowired
    private ShipmentSequenceDAO shipmentSequenceDAO;
    @Autowired
    private CargoBookingTestService cargoBookingTestService;

    @Autowired
    private OperatorAccountDAO operatorAccountDAO;

    @Autowired
    private SMSNotificationDAO smsNotificationDAO;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BranchOfficeDAO branchOfficeDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    private User currentUser;

    private OperatorAccount operatorAccount;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CargoBookingManager cargoBookingManager;

    private ShipmentSequence freeBooking;
    private ShipmentSequence paidBooking;
    private ShipmentSequence toPayBooking;

    @Before
    @After
    public void cleanup() {
        vehicleDAO.deleteAll();
        cargoBookingDAO.deleteAll();
        branchOfficeDAO.deleteAll();
        shipmentSequenceDAO.deleteAll();
        operatorAccountDAO.deleteAll();
        smsNotificationDAO.deleteAll();
        operatorAccount = new OperatorAccount();
        operatorAccount.setSmsSenderName("SRIKRI");
        operatorAccount.setName("test");
        operatorAccount = operatorAccountDAO.save(operatorAccount);
        sessionManager.setOperatorId(operatorAccount.getId());
        userDAO.deleteAll();
        User user = new User();
        user.setUserName("test");
        user.setFirstName("test");
        user.setLastName("last");

        user.setOperatorId(operatorAccount.getId());
        currentUser = userDAO.save(user);
        sessionManager.setCurrentUser(currentUser);
        freeBooking = shipmentSequenceDAO.save(new ShipmentSequence("F", "Free"));
        paidBooking = shipmentSequenceDAO.save(new ShipmentSequence("P", "Paid"));
        toPayBooking = shipmentSequenceDAO.save(new ShipmentSequence("TP", "ToPay"));
    }

    public void testSave() throws Exception {

    }

    @Test(expected = BadRequestException.class)
    public void testSaveWithValidations() throws Exception {
        CargoBooking shipment = new CargoBooking();
        shipmentManager.saveWithValidations(shipment);
    }

    @Test
    public void testSaveWithValidationsNoError() throws Exception {
        CargoBooking shipment = cargoBookingTestService.createNew(freeBooking);
        CargoBooking saved = shipmentManager.saveWithValidations(shipment);
        List<CargoBooking> shipments = IteratorUtils.toList(cargoBookingDAO.findAll().iterator());
        assertEquals(1, shipments.size());
    }

    @Test(expected = BadRequestException.class)
    public void testSaveWithNoDispatchDate() throws Exception {
        CargoBooking shipment = cargoBookingTestService.createNew(freeBooking);
        shipment.setDispatchDate(null);
        shipmentManager.saveWithValidations(shipment);
    }

    @Test
    public void testFindContactInfo() throws Exception {
        CargoBooking shipment = cargoBookingTestService.createNew(freeBooking);
        shipment.setOperatorId(operatorAccount.getId());
        shipment = shipmentManager.saveWithValidations(shipment);
        JSONObject jsonObject = shipmentManager.findContactInfo("from", shipment.getFromContact());
        assertEquals(jsonObject.get("name"), "from");
        assertEquals(jsonObject.get("email"), "email@e.com");
        jsonObject = shipmentManager.findContactInfo("to", shipment.getToContact());
        assertEquals(jsonObject.get("name"), "to");
        assertEquals(jsonObject.get("email"), "to@e.com");
    }

    @Test
    public void testAssignVehicle() throws Exception {
        CargoBooking shipment = cargoBookingTestService.createNew(freeBooking);
        shipment.setOperatorId(operatorAccount.getId());
        CargoBooking shipment1 = cargoBookingTestService.createNew(paidBooking);
        shipment1.setOperatorId(operatorAccount.getId());
        shipment = shipmentManager.saveWithValidations(shipment);
        shipment1 = shipmentManager.saveWithValidations(shipment1);
        CargoBooking shipment2 = cargoBookingTestService.createNew(toPayBooking);
        shipment2.setOperatorId(operatorAccount.getId());
        shipment2 = shipmentManager.saveWithValidations(shipment2);
        List<String> ids = new ArrayList<>();
        ids.add(shipment.getId());
        ids.add(shipment1.getId());

        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo("AP27TU1234");
        vehicle = vehicleDAO.save(vehicle);
        assertTrue(shipmentManager.assignVehicle(vehicle.getId(), ids));
        shipment = cargoBookingDAO.findById(shipment.getId()).get();
        shipment1 = cargoBookingDAO.findById(shipment1.getId()).get();
        shipment2 = cargoBookingDAO.findById(shipment2.getId()).get();

        assertEquals(shipment.getVehicleId(), vehicle.getId());
        assertEquals(shipment1.getVehicleId(), vehicle.getId());
        assertNull(shipment2.getVehicleId());
    }

    @Test
    @Ignore
    public void testGetBranchwiseBookingsSummary() throws Exception{
        BranchOffice b1 = new BranchOffice("B1", "C1");
        b1.setOperatorId(operatorAccount.getId());
        BranchOffice b2 = new BranchOffice("B2", "C2");
        b2.setOperatorId(operatorAccount.getId());
        b1 = branchOfficeDAO.save(b1);
        b2 = branchOfficeDAO.save(b2);
        for(int i=0; i< 10; i++){
            CargoBooking shipment = new CargoBooking();
            if(i%2 == 0){
                shipment.setPaymentType(PaymentStatus.TOPAY.getKey());
            } else {
                shipment.setPaymentType(PaymentStatus.PAID.getKey());
            }
            shipment.setFromEmail("email@e.com");
            shipment.setToEmail("to@e.com");
            shipment.setFromBranchId(b1.getId());
            shipment.setToBranchId(b2.getId());
            shipment.setCargoTransitStatus(CargoTransitStatus.ARRIVED);
            shipment.setFromContact(new Long(1234));
            shipment.setToContact(new Long(12345678));
            shipment.setFromName("from");
            shipment.setToName("to");
            shipment.setTotalCharge(100);
            shipment.setDispatchDate(new Date());
            shipmentManager.saveWithValidations(shipment);
        }
        JSONObject query = new JSONObject();
        query.put("startDate", new Date());
        query.put("endDate", new Date());

        AllBranchBookingSummary summary = shipmentManager.getBranchSummary(query);
        assertEquals(summary.getBranchCargoBookings().size() ,2);
    }

    @Test
    @Ignore
    public void testGetAllBranchBookingsSummary() throws Exception{
        BranchOffice b1 = new BranchOffice("B1", "C1");
        b1.setOperatorId(operatorAccount.getId());
        BranchOffice b2 = new BranchOffice("B2", "C2");
        b2.setOperatorId(operatorAccount.getId());
        b1 = branchOfficeDAO.save(b1);
        b2 = branchOfficeDAO.save(b2);
        for(int i=0; i< 20; i++){
            CargoBooking shipment = new CargoBooking();
            if(i%3 == 0){
                shipment.setPaymentType(PaymentStatus.TOPAY.getKey());
                shipment.setFromBranchId(b1.getId());
                shipment.setToBranchId(b2.getId());
            } else if(i%2 == 0){
                shipment.setPaymentType(PaymentStatus.PAID.getKey());
                shipment.setFromBranchId(b1.getId());
                shipment.setToBranchId(b2.getId());
            }else {
                shipment.setPaymentType(PaymentStatus.PAID.getKey());
                shipment.setFromBranchId(b2.getId());
                shipment.setToBranchId(b1.getId());
            }
            shipment.setFromEmail("email@e.com");
            shipment.setToEmail("to@e.com");
            shipment.setCargoTransitStatus(CargoTransitStatus.ARRIVED);
            shipment.setFromContact(new Long(1234));
            shipment.setToContact(new Long(12345678));
            shipment.setFromName("from");
            shipment.setToName("to");
            shipment.setTotalCharge(100);
            shipment.setDispatchDate(new Date());
            shipmentManager.saveWithValidations(shipment);
        }
        JSONObject q = new JSONObject();
        q.put("startDate", ServiceUtils.formatDate(new Date()));
        q.put("endDate", ServiceUtils.formatDate(new Date()));
        AllBranchBookingSummary summary = shipmentManager.getBranchSummary(q);
        assertEquals(summary.getBranchCargoBookings().size() ,3);
        JSONObject query = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1);
        query.put("startDate", ServiceUtils.formatDate(calendar.getTime()));
        query.put("endDate", ServiceUtils.formatDate(new Date()));
        summary = shipmentManager.getBranchSummary(query);
        assertEquals(summary.getBranchCargoBookings().size() ,2);
        assertEquals(summary.getUserCargoBookingsSummaries().size() ,1);
        UserCargoBookingsSummary userCargoBookingsSummary = summary.getUserCargoBookingsSummaries().get(0);
        assertEquals(userCargoBookingsSummary.getPaidBookingsCount(), 13);
        assertEquals(userCargoBookingsSummary.getPaidBookingsTotal(), 1300,0.0);
        assertEquals(userCargoBookingsSummary.getTopayBookingsCount(), 7);
        assertEquals(userCargoBookingsSummary.getTopayBookingsTotal(), 700, 0.0);
    }

    @Test
    public void testUnloadBookings(){
        CargoBooking shipment = cargoBookingTestService.createNew(freeBooking);
        CargoBooking saved = shipmentManager.saveWithValidations(shipment);
        List<String> ids = new ArrayList<>();
        ids.add(saved.getId());
        shipmentManager.unloadBookings(ids);
    }

    @Test
    public void testFindDeliveredToPayShipmentTotal() throws ParseException {
        BranchOffice b1 = new BranchOffice("B1", "C1");
        b1.setOperatorId(operatorAccount.getId());
        BranchOffice b2 = new BranchOffice("B2", "C2");
        b2.setOperatorId(operatorAccount.getId());
        b1 = branchOfficeDAO.save(b1);
        b2 = branchOfficeDAO.save(b2);
        currentUser.setBranchOfficeId(b2.getId());
        userDAO.save(currentUser);
        List<CargoBooking> cargoBookings = new ArrayList<>();
        for(int i=0; i< 20; i++){
            CargoBooking shipment = new CargoBooking();
            if(i%2 == 0){
                shipment.setPaymentType(PaymentStatus.TOPAY.getKey());
                shipment.setFromBranchId(b1.getId());
                shipment.setToBranchId(b2.getId());
            } else {
                shipment.setPaymentType(PaymentStatus.PAID.getKey());
                shipment.setFromBranchId(b1.getId());
                shipment.setToBranchId(b2.getId());
            }
            shipment.setCargoTransitStatus(CargoTransitStatus.ARRIVED);
            shipment.setFromContact(new Long(1234));
            shipment.setToContact(new Long(12345678));
            shipment.setFromName("from");
            shipment.setToName("to");
            shipment.setTotalCharge(100);
            shipment.setDispatchDate(new Date());
            cargoBookings.add(shipmentManager.saveWithValidations(shipment));
        }
        for(int i=0; i< 20; i++){
            if(i%4 == 0){
                CargoBooking shipment = cargoBookings.get(i);
                shipmentManager.deliverCargoBooking(shipment.getId(), "Notes ");
            }
        }
        BranchDeliverySummary deliverySummary = shipmentManager.findDeliveryShipmentsTotalByBranchUsers(b2.getId(), PaymentStatus.TOPAY, new Date(), new Date());
        assertEquals(deliverySummary.getTotal(), 500, 0.0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -2);
    }

    @Test
    public void testBranchSummaryDaily() throws ParseException {
        OperatorAccount operatorAccount = operatorAccountDAO.save(new OperatorAccount());
        String fromBranchId = null;
        String toBranchId = null;
        for(int i=0; i<10; i++) {
            CargoBooking shipment = null;

            if(i%3 == 0) {
                shipment = cargoBookingTestService.createNew(paidBooking);
            } else {
                shipment = cargoBookingTestService.createNew(toPayBooking);
            }
            fromBranchId = shipment.getFromBranchId();
            toBranchId = shipment.getToBranchId();
            shipment.setOperatorId(operatorAccount.getId());
            shipment = cargoBookingDAO.save(shipment);
        }
        List<CargoBooking> paidBookings = cargoBookingDAO.findByPaymentTypeAndOperatorId(paidBooking.getShipmentType(),
                operatorAccount.getId());
        assertEquals(paidBookings.size() ,4 , 0.0);
        List<CargoBooking> toPayBookings = cargoBookingDAO.findByPaymentTypeAndOperatorId(toPayBooking.getShipmentType(),
                operatorAccount.getId());
        assertEquals(toPayBookings.size() ,6 , 0.0);
        Iterable<Document> summary = shipmentManager.getBranchBookingSummaryByDay(fromBranchId, null);
        assertNotNull(summary.iterator());
        for(Document document: summary){
            if(document.getString("paymentType").equals("ToPay")){
                assertEquals(document.getLong("totalCharge"), 600, 0.0);
            } else if(document.getString("paymentType").equals("Paid")){
                assertEquals(document.getLong("totalCharge"), 400, 0.0);
            }
        }
    }
    @Test
    public void testCargoReviewComment(){
        CargoBooking cargoBooking = new CargoBooking();
       /* cargoBooking.setFromBranchId("58b6f8a68d6ade60a00cfe61");
        cargoBooking.setToBranchId("58b804028d6af7aa278470dd");
        cargoBooking.setForUser("584bdac177c88b7456bfd5f2");
        cargoBooking.setFromContact(new Long(99887));
        cargoBooking.setFromName("samantha");
        cargoBooking.setToContact(new Long(23456));
        cargoBooking.setToName("kalyani");
        cargoBooking.setLoadingCharge(new Long(1200));
        cargoBooking.setOtherCharge(new Long(100));
        cargoBooking.setTotalCharge(new Long(1500));*/
        cargoBooking = cargoBookingDAO.save(cargoBooking);
        shipmentManager.addReviewComment(cargoBooking.getId(), "Test comment");
        cargoBooking = cargoBookingDAO.findById(cargoBooking.getId()).get();
        assertEquals("Test comment", cargoBooking.getReviewComment());
    }
    @Test
    @Ignore
    public void testGroupCargoBookings(){
        BranchOffice b1 = new BranchOffice();
        b1.setOperatorId(operatorAccount.getId());
        b1 = branchOfficeDAO.save(b1);
        BranchOffice b2 = new BranchOffice();
        b2.setOperatorId(operatorAccount.getId());

        b2 = branchOfficeDAO.save(b2);
        BranchOffice b3 = new BranchOffice();
        b3.setOperatorId(operatorAccount.getId());

        b3 =  branchOfficeDAO.save(b3);
        Calendar calendar = Calendar.getInstance();
        for(int i=0;i<10;i++){
            CargoBooking cargoBooking = new CargoBooking();
            cargoBooking.setOperatorId(operatorAccount.getId());
            cargoBooking.setDispatchDate(calendar.getTime());
            cargoBooking.setTotalCharge(500);
            if(i%2 == 0 && i<5){
                cargoBooking.setFromBranchId(b2.getId());
            }
            else if(i == 5 && i == 7){
                cargoBooking.setFromBranchId(b1.getId());

            }else{
                cargoBooking.setFromBranchId(b3.getId());
            }

            cargoBookingDAO.save(cargoBooking);

        }
        JSONObject results = cargoBookingManager.groupCargoBookings();
        //assertEquals(2,results.size());
    }

}