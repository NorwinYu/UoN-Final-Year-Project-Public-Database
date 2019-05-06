package com.leave.services;

import java.util.List;

import com.leave.dtos.EmployeeDetailsDTO;
import com.leave.dtos.LeaveDetailsDTO;
import com.leave.dtos.LeavesDetailsByEmployeeDTO;
import com.leave.obj.Employee;
import com.leave.obj.Leave;
import com.leave.obj.LeaveOne;

public interface EmployeeDataService {
	
	EmployeeDetailsDTO getEmployeeData(Integer id);
	EmployeeDetailsDTO postLeaveAccepted(LeaveDetailsDTO leave);
	EmployeeDetailsDTO postLeaveDenied(LeaveDetailsDTO leave);
	Leave postAddLeave(Leave leave);
	Employee postUpdateEmployee(Integer id, Integer newTime);
	List<Leave> getLeaveData(Integer id);
	List<LeavesDetailsByEmployeeDTO> getAllPendingLeaves();
	List<LeaveOne> getLeaveDataOne(Integer id);
	EmployeeDetailsDTO getEmployeeDataById(Integer id);
	EmployeeDetailsDTO getEmployeeDataByIdFull(Integer id);
}
