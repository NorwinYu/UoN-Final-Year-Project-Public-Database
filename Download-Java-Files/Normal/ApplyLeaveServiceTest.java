package com.leave.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.applyleave.service.ApplyLeaveService;
import com.leave.obj.Leave;
import com.leave.obj.LeaveErrors;
import com.leave.obj.LeaveRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplyLeaveServiceTest {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ApplyLeaveService applyLeaveService;
	
	private LeaveErrors response;
	private LeaveRequest lr;
	
	@Before
	public void instantiateController(){
		lr = new LeaveRequest();
		response = new LeaveErrors();
	}

	@Test
	public void testLeaveEmpty() {
		
		System.out.println("in Leave test - LeaveEmpty");
		//date fields, and leave type empty
		doNormalRequest();
		
		assertEquals("Leave Validation",2,response.errorCount());
		response.getErrors().forEach(System.out::println);
	}
	
	@Test
	public void testLeaveInvalidDates() {
		
		System.out.println("in Leave test - InvalidDates");
	
		Calendar cal = Calendar.getInstance();
		cal.set(2022,2,15);
		Date date1 = cal.getTime();
		
		cal.set(2019,1,15);
		Date date2 = cal.getTime();
		
		cal.set(2022,2,20);
		Date date3 = cal.getTime();
		
		cal.set(2019,1,18);
		Date date4 = cal.getTime();
		
		lr.setLeaveType("Earned leave/Privileged leave");
		lr.setStartDate(date2);
		lr.setEndDate(date4);
		
		doNormalRequest();
		assertEquals("Dates must be after today",2,response.errorCount());
		response.getErrors().forEach(System.out::println);
		
		lr.setStartDate(date1);
		doNormalRequest();
		assertEquals("Start must be before end date",2,response.errorCount());
		response.getErrors().forEach(System.out::println);
		
		lr.setEndDate(date3);
		doNormalRequest();
		assertEquals("Incorrect num of days",1,response.errorCount());
		response.getErrors().forEach(System.out::println);
	}
	
	@Test
	public void testLeaveDateOverlap() {
		
		System.out.println("in Leave test - DateOverlap");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2019,2,15);
		Date date1 = cal.getTime();
		
		cal.set(2019,0,15);
		Date date2 = cal.getTime();
		
		cal.set(2019,2,20);
		Date date3 = cal.getTime();
		
		cal.set(2019,1,15);
		Date date4 = cal.getTime();
		
		Leave testing1 = new Leave();
		testing1.setStartDate(date2);
		testing1.setEndDate(date1);
		
		Leave testing2 = new Leave();
		testing2.setStartDate(date4);
		testing2.setEndDate(date3);
		
		assertTrue("Dates overlap",applyLeaveService.checkOverlap(testing1,testing2));
		assertTrue("Dates overlap swap",applyLeaveService.checkOverlap(testing2,testing1));
		
		testing1.setStartDate(date2);
		testing1.setEndDate(date3);
		
		testing2.setStartDate(date4);
		testing2.setEndDate(date1);
		
		assertTrue("Dates inside",applyLeaveService.checkOverlap(testing1,testing2));
		assertTrue("Dates inside swap",applyLeaveService.checkOverlap(testing2,testing1));
		
		testing1.setStartDate(date2);
		testing1.setEndDate(date4);
		
		testing2.setStartDate(date1);
		testing2.setEndDate(date3);
		
		assertFalse("Dates do not overlap",applyLeaveService.checkOverlap(testing1,testing2));
		assertFalse("Dates do not overlap swap",applyLeaveService.checkOverlap(testing2,testing1));
		
	}
	
	private void doNormalRequest(){
		try {
			response = new ObjectMapper().readValue( mvc.perform(MockMvcRequestBuilders.post("/applyLeave")
			  .content(asJsonString(lr))
			  .contentType(MediaType.APPLICATION_JSON)
			  .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString(), LeaveErrors.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
}
