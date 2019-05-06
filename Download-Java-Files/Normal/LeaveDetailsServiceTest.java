package com.leave.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.leave.dtos.LeaveDetailsDTO;
import com.leave.dtos.LeavesDetailsByEmployeeDTO;
import com.leave.obj.Employee;
import com.leave.obj.Leave;
import com.leave.repositories.EmployeeRepository;
import com.leave.repositories.LeaveRepository;


public class LeaveDetailsServiceTest {
	
	@Mock
	LeaveRepository leaveRepository;
	@Mock
	EmployeeRepository employeeRepository;
	
	LeaveDetailsService leavedetailsService;
	Leave mLeave;
	Employee mEmployee;
	LeaveDetailsDTO mLeaveDetailsDTO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		leavedetailsService = new LeaveDetailsService(leaveRepository, employeeRepository);
		
		mLeave = new Leave();
		mLeave.setLeaveID(1);
		mLeave.setStartDate(Date.valueOf("2019-01-01"));
		mLeave.setEndDate(Date.valueOf("2019-01-02"));
		mLeave.setLeaveStatus("pending");
		mLeave.setManagerComments("Manager comments");
		mLeave.setNumDays(2);
		mLeave.setLeaveType("Vacations");
		
		mEmployee = new Employee();
		mEmployee.setEmpId(1);
		mEmployee.setEmpName("Dummy");
		mEmployee.setEmpDept("Development");
		mEmployee.setEmpMail("a@a.c");
		mEmployee.setEmpPhone(1234);
		mEmployee.setLeaveBalance(10);
		mEmployee.setEmpMngId(2);

		mLeave.setEmployee(mEmployee);
		mEmployee.setLeaves(Arrays.asList(mLeave));
		
		mLeaveDetailsDTO = new LeaveDetailsDTO();
		mLeaveDetailsDTO.setLeaveId(1);
		mLeaveDetailsDTO.setEmployeeId(1);
		mLeaveDetailsDTO.setEmployeeId(1);
		mLeaveDetailsDTO.setEmployeeName("Dummy");
		mLeaveDetailsDTO.setLeaveBalance(10);
		mLeaveDetailsDTO.setStartDate("2019-01-01");
		mLeaveDetailsDTO.setEndDate("2019-01-02");
		mLeaveDetailsDTO.setStatus("accepted");
		mLeaveDetailsDTO.setManagerComments("Manager comments");
		mLeaveDetailsDTO.setDays(2);
		mLeaveDetailsDTO.setLeaveType("Vacations");
		
		
	}

	@Test
	public void getEmployeesDataTest() {
			
		when(leaveRepository.findOne(any(Integer.class))).thenReturn(mLeave);
		
		when(employeeRepository.findOne(any(Integer.class))).thenReturn(mEmployee);
		
		LeaveDetailsDTO leaveDetailsDTO = (LeaveDetailsDTO) leavedetailsService.getEmployeeData(1);
		
		assertEquals(mEmployee.getEmpId(),leaveDetailsDTO.getEmployeeId());
		assertEquals(mEmployee.getEmpName(),leaveDetailsDTO.getEmployeeName());
		assertEquals(mEmployee.getLeaveBalance(), leaveDetailsDTO.getLeaveBalance());
		
		assertEquals(mLeave.getLeaveID(), leaveDetailsDTO.getLeaveId());
		assertEquals("pending", mLeave.getLeaveStatus());
				
	}
	
	@Test
	public void postLeaveAcceptedTest() {
		
		when(leaveRepository.findOne(any(Integer.class))).thenReturn(mLeave);
		
		when(employeeRepository.findOne(any(Integer.class))).thenReturn(mEmployee);
		
		LeaveDetailsDTO leaveDetailsDTO = (LeaveDetailsDTO) leavedetailsService.postLeaveAccepted(mLeaveDetailsDTO);

		
		assertEquals(mEmployee.getEmpId(),leaveDetailsDTO.getEmployeeId());
		assertEquals(mEmployee.getEmpName(),leaveDetailsDTO.getEmployeeName());
		assertEquals(mEmployee.getLeaveBalance(), leaveDetailsDTO.getLeaveBalance());
		
		assertEquals(mLeave.getLeaveID(), leaveDetailsDTO.getLeaveId());
		assertEquals("approved", mLeave.getLeaveStatus());
	}
	
	@Test
	public void postLeaveDeniedTest() {
		
		when(leaveRepository.findOne(any(Integer.class))).thenReturn(mLeave);
		
		when(employeeRepository.findOne(any(Integer.class))).thenReturn(mEmployee);
		
		LeaveDetailsDTO leaveDetailsDTO = (LeaveDetailsDTO) leavedetailsService.postLeaveDenied(mLeaveDetailsDTO);

		
		assertEquals(mEmployee.getEmpId(),leaveDetailsDTO.getEmployeeId());
		assertEquals(mEmployee.getEmpName(),leaveDetailsDTO.getEmployeeName());
		assertEquals(mEmployee.getLeaveBalance(), leaveDetailsDTO.getLeaveBalance());
		
		assertEquals(mLeave.getLeaveID(), leaveDetailsDTO.getLeaveId());
		assertEquals("denied", mLeave.getLeaveStatus());
	}
	
	@Test
	public void getAllPendingLeaves() {
		
		Employee employee = new Employee();
		employee.setEmpId(1);
		mLeave.setEmployee(employee);
		
		
		when(leaveRepository.findLeaveByStatus(AcceptanceEnum.pending.name())).thenReturn(Arrays.asList(mLeave));
		
		List<LeavesDetailsByEmployeeDTO> leavesDetailsByEmployeeDTOs = leavedetailsService.getAllPendingLeaves();
		
		assertEquals(1, leavesDetailsByEmployeeDTOs.size());
		assertEquals(mLeave.getLeaveID(), leavesDetailsByEmployeeDTOs.get(0).getLeaves().get(0).getLeaveId());
		assertEquals("pending", leavesDetailsByEmployeeDTOs.get(0).getLeaves().get(0).getStatus());
		
		
	}
	

}
