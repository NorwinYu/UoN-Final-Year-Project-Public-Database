package com.leave.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leave.dtos.EmployeeContactDetailsDTO;
import com.leave.dtos.EmployeeDetailsDTO;
import com.leave.dtos.LeaveDetailsDTO;
import com.leave.dtos.LeavesDetailsByEmployeeDTO;
import com.leave.exceptions.IncorrectLeaveBalanceException;
import com.leave.exceptions.LeaveDetailsNotFoundException;
import com.leave.exceptions.UserNotFoundException;
import com.leave.obj.Employee;
import com.leave.obj.Leave;
import com.leave.obj.LeaveOne;
import com.leave.repositories.EmployeeRepository;
import com.leave.repositories.LeaveRepository;
import com.leave.repositories.LeaveRepositoryOne;

@Service
@Qualifier(value="LeaveDetailsService")
public class LeaveDetailsService implements EmployeeDataService {

	private LeaveRepository leaveRepository;
	private LeaveRepositoryOne leaveRepositoryOne;
	private EmployeeRepository employeeRepository;

	public LeaveDetailsService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository) {
		this.leaveRepository = leaveRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public EmployeeDetailsDTO getEmployeeData(Integer id) {
		Leave leave = Optional.ofNullable(leaveRepository.findOne(id))
				.orElseThrow(()-> new LeaveDetailsNotFoundException("Not leave details Available")); 

		Employee employee = Optional.ofNullable(employeeRepository.findOne
				(
						Optional.ofNullable(leave.getEmployee())
						.orElseThrow(()-> new UserNotFoundException("Not employee available")).getEmpId() 	
						)
				)
				.orElseThrow(()-> new UserNotFoundException("Not employee available")); 

		LeaveDetailsDTO leaveDetailsDTO = leaveEntityToLeaveDetailsDTO(leave);

		leaveDetailsDTO.setEmployeeId(employee.getEmpId());
		leaveDetailsDTO.setEmployeeName(Optional.ofNullable(employee.getEmpName()).orElse(" "));
		leaveDetailsDTO.setLeaveBalance(Optional.ofNullable(employee.getLeaveBalance()).orElse(0));
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		leaveDetailsDTO.setAppliedOn(df.format(leave.getAppliedOn()));

		return leaveDetailsDTO;
	}

	@Override
	public EmployeeDetailsDTO postLeaveAccepted(LeaveDetailsDTO leave) {

		if(leave.getStatus().equals(AcceptanceEnum.approved.name())||
				leave.getStatus().equals(AcceptanceEnum.denied.name()))
			return null;

		Leave dbLeave = Optional.ofNullable(leaveRepository.findOne(leave.getLeaveId()))
				.orElseThrow(()-> new LeaveDetailsNotFoundException("Not leave details Available")); 

		Employee employee = Optional.ofNullable(employeeRepository.findOne(dbLeave.getEmployee().getEmpId()))
				.orElseThrow(()-> new UserNotFoundException("User not found in given leave details"));

		leave.setStatus(AcceptanceEnum.approved.name());
		leave.setLeaveBalance(employee.getLeaveBalance()-leave.getDays());

		if(leave.getLeaveBalance()<0)
			throw new IncorrectLeaveBalanceException("Not enough days");

		dbLeave.setLeaveStatus(leave.getStatus());
		dbLeave.setManagerComments(leave.getManagerComments());
		employee.setLeaveBalance(leave.getLeaveBalance());

		leaveRepository.save(dbLeave);
		employeeRepository.save(employee);

		return leave;
	}

	@Override
	public EmployeeDetailsDTO postLeaveDenied(LeaveDetailsDTO leave) {

		if(leave.getStatus().equals(AcceptanceEnum.approved.name())||
				leave.getStatus().equals(AcceptanceEnum.denied.name()))
			return null;

		Leave dbLeave = Optional.ofNullable(leaveRepository.findOne(leave.getLeaveId()))
				.orElseThrow(()-> new LeaveDetailsNotFoundException("Not leave details Available")); 

		leave.setStatus(AcceptanceEnum.denied.name());
		dbLeave.setLeaveStatus(leave.getStatus());

		leaveRepository.save(dbLeave);

		return leave;
	}

	@Override
	public Leave postAddLeave(Leave leave) {

		leave.setLeaveStatus(AcceptanceEnum.pending.name());

		leaveRepository.save(leave);

		return leave;
	}

	@Override
	public Employee postUpdateEmployee(Integer id, Integer newTime) {
		Employee employee = Optional.ofNullable(employeeRepository.findOne
				(
						Optional.ofNullable(id)
						.orElseThrow(()-> new UserNotFoundException("Not employee available"))
						)
				)
				.orElseThrow(()-> new UserNotFoundException("Not employee available")); 
		employee.setLeaveBalance(newTime);
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public List<Leave> getLeaveData(Integer id) {

		List<Leave> leaves = Optional.ofNullable(leaveRepository.findLeaveByAttribute(id))
				.orElseThrow(()-> new LeaveDetailsNotFoundException("Not leave details Available")); 

		return leaves;
	}
	@Override
	public List<LeaveOne> getLeaveDataOne(Integer id) {
		//List<LeaveOne> leaves = leaveRepositoryOne.findLeaveByAttribute(id); 
		//return leaves;
		
		Configuration config = new Configuration();
        SessionFactory factory = new Configuration().configure(
				"/com/leave/hibernate.cfg.xml")
				.buildSessionFactory();
		 org.hibernate.Session sess = factory.openSession();
		try{
		     List <LeaveOne>products ;
		    org.hibernate.Transaction tx = sess.beginTransaction();
		    //products = sess.createSQLQuery("SELECT * FROM leave_history where emp_id="+id).list();
		    products = sess.createSQLQuery("SELECT * FROM leave_history where emp_id="+id).addEntity(LeaveOne.class).list();
		    if(products.size() > 0)
		    {
		        return products;
		    	//return (LeaveOne)products.get(0);
		    	//for (int i = 0; i < products.size(); i++) {
		    		
		    	//	Object[] stringValues = (Object[]) products.get(i);
		    		
		    	//}
		    }
		    	return null;  
		    }
	    catch(Exception e)
	    {
	        throw e;
	    }
		 
	}
	
	public EmployeeDetailsDTO getEmployeeDataById(Integer id) {
		Employee employee = Optional.ofNullable(employeeRepository.findOne(id))
				.orElseThrow(()-> new UserNotFoundException("Employee not found")); 

		EmployeeDetailsDTO toReturn = new EmployeeDetailsDTO();
		toReturn.setEmployeeId(employee.getEmpId());
		toReturn.setEmployeeName(employee.getEmpName());
		toReturn.setLeaveBalance(employee.getLeaveBalance());
		return toReturn;
	}
	
	public EmployeeDetailsDTO getEmployeeDataByIdFull(Integer id) {
		Employee employee = Optional.ofNullable(employeeRepository.findOne(id))
				.orElseThrow(()-> new UserNotFoundException("Employee not found")); 

		EmployeeContactDetailsDTO toReturn = new EmployeeContactDetailsDTO();
		toReturn.setEmployeeId(employee.getEmpId());
		toReturn.setEmployeeName(employee.getEmpName());
		toReturn.setLeaveBalance(employee.getLeaveBalance());
		toReturn.setEmpDept(employee.getEmpDept());
		toReturn.setEmpDoj(employee.getEmpDoj());
		toReturn.setEmpMail(employee.getEmpMail());
		toReturn.setEmpPhone(employee.getEmpPhone());
		toReturn.setEmpMngId(employee.getEmpMngId());
		
		return toReturn;
	}

	@Override
	public List<LeavesDetailsByEmployeeDTO> getAllPendingLeaves() {

		List<Leave> leaves = Optional.ofNullable(leaveRepository.findLeaveByStatus(AcceptanceEnum.pending.name()))
				.orElseThrow(()-> new LeaveDetailsNotFoundException("Not leave details Available")); 	

		List<LeavesDetailsByEmployeeDTO> pendingLeavesDTO = new ArrayList<>();
		
		

		Map<Employee, List<Leave>> pendingLeavesByEmployee = leaves.stream()
				.filter(leave -> {
					if(!Optional.ofNullable(leave.getEmployee()).isPresent())
						throw new UserNotFoundException("Employee details not available");
					return true;
				}
						)
				.collect(Collectors.groupingBy(x -> x.getEmployee()));

		pendingLeavesByEmployee.entrySet().stream().forEach(employee->{

			LeavesDetailsByEmployeeDTO leaveDetailsByEmployee = new LeavesDetailsByEmployeeDTO();
			leaveDetailsByEmployee.setEmployeeId(employee.getKey().getEmpId());
			leaveDetailsByEmployee.setEmployeeName(employee.getKey().getEmpName());
			leaveDetailsByEmployee.setLeaveBalance(employee.getKey().getLeaveBalance());
			List<LeaveDetailsDTO> leaveDetails = new ArrayList<>();

			employee.getValue().stream().forEach(leave -> {

				LeaveDetailsDTO leaveDetail = leaveEntityToLeaveDetailsDTO(leave);

				leaveDetail.setEmployeeId(leaveDetailsByEmployee.getEmployeeId());
				leaveDetail.setEmployeeName(leaveDetailsByEmployee.getEmployeeName());
				leaveDetail.setLeaveBalance(leaveDetailsByEmployee.getLeaveBalance());

				leaveDetails.add(leaveDetail);

			});

			leaveDetailsByEmployee.setLeaves(leaveDetails);

			pendingLeavesDTO.add(leaveDetailsByEmployee);

		});

		return pendingLeavesDTO;

	}


	private LeaveDetailsDTO leaveEntityToLeaveDetailsDTO(Leave leave) {

		LeaveDetailsDTO leaveDetailsDTO = new LeaveDetailsDTO();

		leaveDetailsDTO.setLeaveId(leave.getLeaveID());
		leaveDetailsDTO.setEndDate(Optional.ofNullable(leave.getEndDate()).orElse(Date.from(Instant.now())).toString());
		leaveDetailsDTO.setReason(Optional.ofNullable(leave.getReasons()).orElse(" "));
		leaveDetailsDTO.setDays(Optional.ofNullable(leave.getNumDays()).orElse(0));
		leaveDetailsDTO.setLeaveType(Optional.ofNullable(leave.getLeaveType()).orElse(" "));

		if(Optional.ofNullable(leave.getEndDate()).isPresent())
			leaveDetailsDTO.setEndDate(new SimpleDateFormat("MM/dd/yyyy").format(leave.getEndDate()).toString());
		else
			leaveDetailsDTO.setEndDate(" ");


		if(Optional.ofNullable(leave.getStartDate()).isPresent())
			leaveDetailsDTO.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(leave.getStartDate()).toString());
		else
			leaveDetailsDTO.setStartDate(" ");

		leaveDetailsDTO.setStatus(Optional.ofNullable(leave.getLeaveStatus()).orElse(" "));
		leaveDetailsDTO.setManagerComments(Optional.ofNullable(leave.getManagerComments()).orElse(" "));

		return leaveDetailsDTO;	

	}

}

enum AcceptanceEnum{
	approved, denied, pending
}


