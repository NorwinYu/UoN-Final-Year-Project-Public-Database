package com.leave.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leave.dtos.LeaveDetailsDTO;
import com.leave.dtos.LeavesDetailsByEmployeeDTO;
import com.leave.services.EmployeeDataService;

@Controller
@RequestMapping(value="/leave/details")
@CrossOrigin(origins="*")
public class LeaveAcceptanceController {

	private EmployeeDataService employeeDataService;

	public LeaveAcceptanceController(@Qualifier("LeaveDetailsService")EmployeeDataService employeeDataService) {
		this.employeeDataService = employeeDataService;
	}

	/**
	 * 
	 * getLeaveDetails
	 * 
	 * Retrieve data related to employee leave.
	 * @param	id		Leave id to retrieve data.
	 * @return	{@link LeaveDetailsDTO} Data related to the employee leave.
	 *
	 */
	@RequestMapping("/{id}")
	public ResponseEntity<LeaveDetailsDTO> getLeaveDetails(@PathVariable Integer id){
		return new ResponseEntity<LeaveDetailsDTO>((LeaveDetailsDTO) employeeDataService.getEmployeeData(id),
				HttpStatus.OK);
	}

	/**
	 * 
	 * postLeaveAccepted
	 * 
	 * Retrieve data related to employee leave.
	 * @param	LeaveDetailsDTO		Leave data dto related to the accepted leave.
	 * @return	{@link LeaveDetailsDTO} Data related to the employee accepted accepted.
	 *
	 */
	@RequestMapping(value="/accepted",headers="content-type=application/json")
	public ResponseEntity<LeaveDetailsDTO> postLeaveAccepted(@RequestBody LeaveDetailsDTO leaveDetailsDTO){
		return new ResponseEntity<LeaveDetailsDTO>((LeaveDetailsDTO) employeeDataService.postLeaveAccepted(leaveDetailsDTO),
				HttpStatus.OK);
	}

	/**
	 * 
	 * postLeaveDenied
	 * 
	 * Retrieve data related to employee leave.
	 * @param	LeaveDetailsDTO		Leave data dto related to the denied leave.
	 * @return	{@link LeaveDetailsDTO} Data related to the employee denied leave.
	 *
	 */
	@RequestMapping(value="/denied",headers="content-type=application/json")
	public ResponseEntity<LeaveDetailsDTO> postLeaveDenied(@RequestBody LeaveDetailsDTO leaveDetailsDTO){
		return new ResponseEntity<LeaveDetailsDTO>((LeaveDetailsDTO) employeeDataService.postLeaveDenied(leaveDetailsDTO),
				HttpStatus.OK);
	}
	
	/**
	 * 
	 * getAllPendignLeaves
	 * 
	 * Retrieve data related to employee leave.
	 * @return	{@link LeaveDetailsByEmployeeDTO} List of pending leaves by employee.
	 *
	 */
	@RequestMapping(value="/pending")
	public ResponseEntity<Iterator<LeavesDetailsByEmployeeDTO>> getAllPendingLeaves(){
		ResponseEntity<Iterator<LeavesDetailsByEmployeeDTO>> n = 
				new ResponseEntity<Iterator<LeavesDetailsByEmployeeDTO>>(employeeDataService.getAllPendingLeaves().iterator(),
				HttpStatus.OK);
		
		return n;
	}
	
	

}
