package com.leave.employeedetails;

import org.springframework.web.bind.annotation.RestController;

import com.leave.applyleave.service.ApplyLeaveService;
import com.leave.dtos.EmployeeContactDetailsDTO;
import com.leave.dtos.EmployeeDetailsDTO;
import com.leave.obj.Leave;
import com.leave.obj.LeaveErrors;
import com.leave.obj.LeaveRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeDetailsController {

	@Autowired
    EmployeeService employeeService;

    @RequestMapping(value="/details/{empId}", method = RequestMethod.GET)
    @ResponseBody
    public EmployeeContactDetailsDTO employee(@PathVariable("empId") int empId) {
    	
        return employeeService.getEmpDetails(empId); 
    }
}
    
  