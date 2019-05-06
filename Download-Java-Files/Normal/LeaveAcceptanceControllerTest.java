package com.leave.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leave.controller.LeaveAcceptanceController;
import com.leave.dtos.LeaveDetailsDTO;
import com.leave.dtos.LeavesDetailsByEmployeeDTO;
import com.leave.services.EmployeeDataService;

public class LeaveAcceptanceControllerTest {
	
	@Mock
	EmployeeDataService employeeDataService;

	@Autowired
	private MockMvc mockMvc;
	
	LeaveDetailsDTO mLeaveDetailsDTO; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(new LeaveAcceptanceController(employeeDataService)).build();
		
		mLeaveDetailsDTO = new LeaveDetailsDTO();
		mLeaveDetailsDTO.setLeaveId(1);
		mLeaveDetailsDTO.setEmployeeId(1);
		mLeaveDetailsDTO.setEmployeeName("Dummy");
		mLeaveDetailsDTO.setLeaveBalance(10);
		mLeaveDetailsDTO.setStartDate("02/01/2019");
		mLeaveDetailsDTO.setEndDate("02/02/2019");
		mLeaveDetailsDTO.setStatus("pending");
		mLeaveDetailsDTO.setManagerComments("Manager comments");
		mLeaveDetailsDTO.setDays(2);
		mLeaveDetailsDTO.setLeaveType("Vacations");
	}

	@Test
	public void getLeaveDetailsTest() throws Exception {
		
		mLeaveDetailsDTO.setStatus("pending");
		
		when(employeeDataService.getEmployeeData(any(Integer.class))).thenReturn(mLeaveDetailsDTO);
		
		mockMvc.perform(get("/leave/details/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("employeeId").value(1))
				.andExpect(jsonPath("employeeName").value("Dummy"))
				.andExpect(jsonPath("endDate").value("02/02/2019"))
				.andExpect(jsonPath("startDate").value("02/01/2019"))
				.andExpect(jsonPath("managerComments").value("Manager comments"))
				.andExpect(jsonPath("days").value("2"))
				.andExpect(jsonPath("leaveType").value("Vacations"))
				.andExpect(jsonPath("status").value("pending"));
	}
	
	@Test
	public void postLeaveAcceptedTest() throws Exception {
		mLeaveDetailsDTO.setStatus("accepted");

		when(employeeDataService.postLeaveAccepted(any(LeaveDetailsDTO.class))).thenReturn(mLeaveDetailsDTO);
		
		LeaveDetailsDTO acceptedLeaveDetailsDTO = new LeaveDetailsDTO();
		acceptedLeaveDetailsDTO.setLeaveId(1);
		acceptedLeaveDetailsDTO.setEmployeeId(1);
		acceptedLeaveDetailsDTO.setEmployeeName("Dummy");
		acceptedLeaveDetailsDTO.setLeaveBalance(10);
		acceptedLeaveDetailsDTO.setStartDate("02/01/2019");
		acceptedLeaveDetailsDTO.setEndDate("02/02/2019");
		acceptedLeaveDetailsDTO.setStatus("pending");
		acceptedLeaveDetailsDTO.setManagerComments("Manager comments");
		acceptedLeaveDetailsDTO.setDays(2);
		acceptedLeaveDetailsDTO.setLeaveType("Vacations");
		acceptedLeaveDetailsDTO.setAcceptance("accepted");
		
		String acceptedJson = new ObjectMapper().writeValueAsString(acceptedLeaveDetailsDTO);
		
		mockMvc.perform(post("/leave/details/accepted")
						.contentType(MediaType.APPLICATION_JSON)
						.content(acceptedJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("employeeId").value(1))
				.andExpect(jsonPath("employeeName").value("Dummy"))
				.andExpect(jsonPath("endDate").value("02/02/2019"))
				.andExpect(jsonPath("startDate").value("02/01/2019"))
				.andExpect(jsonPath("managerComments").value("Manager comments"))
				.andExpect(jsonPath("days").value("2"))
				.andExpect(jsonPath("leaveType").value("Vacations"))
				.andExpect(jsonPath("status").value("accepted"));
	}
	
	@Test
	public void postLeaveDeniedTest() throws Exception {
		mLeaveDetailsDTO.setStatus("denied");
		
		LeaveDetailsDTO deniedLeaveDetailsDTO = new LeaveDetailsDTO();
		deniedLeaveDetailsDTO.setLeaveId(1);
		deniedLeaveDetailsDTO.setEmployeeId(1);
		deniedLeaveDetailsDTO.setEmployeeName("Dummy");
		deniedLeaveDetailsDTO.setLeaveBalance(10);
		deniedLeaveDetailsDTO.setStartDate("02/01/2019");
		deniedLeaveDetailsDTO.setEndDate("02/02/2019");
		deniedLeaveDetailsDTO.setStatus("pending");
		deniedLeaveDetailsDTO.setManagerComments("Manager comments");
		deniedLeaveDetailsDTO.setDays(2);
		deniedLeaveDetailsDTO.setLeaveType("Vacations");
		deniedLeaveDetailsDTO.setAcceptance("denied");
		
		String deniedJson = new ObjectMapper().writeValueAsString(deniedLeaveDetailsDTO);
		
		when(employeeDataService.postLeaveDenied(any(LeaveDetailsDTO.class))).thenReturn(mLeaveDetailsDTO);
		
		mockMvc.perform(
					post("/leave/details/denied")
					.contentType(MediaType.APPLICATION_JSON)
					.content(deniedJson)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("employeeId").value(1))
				.andExpect(jsonPath("employeeName").value("Dummy"))
				.andExpect(jsonPath("endDate").value("02/02/2019"))
				.andExpect(jsonPath("startDate").value("02/01/2019"))
				.andExpect(jsonPath("managerComments").value("Manager comments"))
				.andExpect(jsonPath("days").value("2"))
				.andExpect(jsonPath("leaveType").value("Vacations"))
				.andExpect(jsonPath("status").value("denied"));
	}
	
	@Test
	public void getAllPendingTest() throws Exception {
		
		mLeaveDetailsDTO.setStatus("pending");
		
		LeavesDetailsByEmployeeDTO leavesDetailsByEmployeeDTO = new LeavesDetailsByEmployeeDTO();
		
		leavesDetailsByEmployeeDTO.setEmployeeId(1);
		leavesDetailsByEmployeeDTO.setEmployeeName("Dummy");
		leavesDetailsByEmployeeDTO.setLeaveBalance(10);
		leavesDetailsByEmployeeDTO.setLeaves(Arrays.asList(mLeaveDetailsDTO));
		

		when(employeeDataService.getAllPendingLeaves()).thenReturn(Arrays.asList(leavesDetailsByEmployeeDTO));
		
		mockMvc.perform(get("/leave/details/pending"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].employeeId").value(1))
				.andExpect(jsonPath("$[0].employeeName").value("Dummy"))
				.andExpect(jsonPath("$[0].leaveBalance").value(10))
				.andExpect(jsonPath("$[0].leaves").isArray());
	}

}
