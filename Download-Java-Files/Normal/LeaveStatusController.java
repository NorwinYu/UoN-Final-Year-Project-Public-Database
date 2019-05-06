package com.leave.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leave.dtos.LeaveDetailsDTO;
import com.leave.obj.LeaveOne;
import com.leave.services.EmployeeDataService;

@Controller
@RequestMapping(value="/leave/status")
@CrossOrigin(origins="*")
public class LeaveStatusController {

	private EmployeeDataService employeeDataService;

	public LeaveStatusController(@Qualifier("LeaveDetailsService")EmployeeDataService employeeDataService) {
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
		//System.out.println("response"+employeeDataService.getLeaveData(id));
		return new ResponseEntity(employeeDataService.getLeaveDataOne(id),
				HttpStatus.OK);
	}

}
