package com.leave.employeedetails;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leave.dtos.EmployeeContactDetailsDTO;
import com.leave.services.EmployeeDataService;

@Service("employeeService")
public class EmployeeService {
    private EmployeeDataService employeeService;

	public EmployeeService(@Qualifier("LeaveDetailsService")EmployeeDataService employeeDataService) {
		this.employeeService = employeeDataService;
	}
	
	public EmployeeContactDetailsDTO getEmpDetails(int empId){
		
		return (EmployeeContactDetailsDTO)employeeService.getEmployeeDataByIdFull(empId);
		
	}
}

