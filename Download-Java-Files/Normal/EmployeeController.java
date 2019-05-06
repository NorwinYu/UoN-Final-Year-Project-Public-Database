package com.hex.leaverequest.controller;

import java.util.List;

import com.hex.leaverequest.model.Employee;
import com.hex.leaverequest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Employee> getEmployees() {
		
		List<Employee> listOfEmployees = employeeService.getAllEmployees();
		return listOfEmployees;
	}

	@RequestMapping(value = "/getEmployee/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Employee getEmployeeById(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addCountry(@RequestBody Employee employee) {	
		employeeService.addEmployee(employee);
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateEmployee(@RequestBody Employee employee) {
		employeeService.updateEmployee(employee);
	}

	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployee(id);		
	}	
	
	@RequestMapping(value = "/getManager/{empId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Employee getManagerByEmpId(@PathVariable int empId) {
		return employeeService.getManager(empId);
	}
}
