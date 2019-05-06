package com.leave.applyleave.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.leave.dtos.EmployeeDetailsDTO;
import com.leave.obj.Leave;
import com.leave.obj.LeaveErrors;
import com.leave.services.EmployeeDataService;

@Service("applyLeaveService")
public class ApplyLeaveService {
	
	private EmployeeDataService employeeService;

	public ApplyLeaveService(@Qualifier("LeaveDetailsService")EmployeeDataService employeeDataService) {
		this.employeeService = employeeDataService;
	}
	
	 /**
     * dbLeave
     * 
	 * Does database validation of given leave before
	 * adding to database as needed
     * 
     * @param leave - the Leave object to be added to db
     * @return a list of errors, blank if valid
     */
    public LeaveErrors dbLeave(Leave leave){
    	
    	LeaveErrors errorList = new LeaveErrors();

    	//TODO if request fails

		EmployeeDetailsDTO emp = (EmployeeDetailsDTO) employeeService.getEmployeeData(leave.getEmployee().getEmpId());
		List<Leave> leaves = employeeService.getLeaveData(emp.getEmployeeId());
		int emplDaysLeft = emp.getLeaveBalance();
		
		//make sure employee has enough days
    	if (leave.getNumDays() <= emplDaysLeft){
    		//make sure no overlaps between any of the leaves
    		leaves.forEach((toCheck) -> {
    			if (checkOverlap(toCheck,leave)){
    				errorList.addError("Error: Leave overlaps with other leaves");
    				return;
    			}
			});
    		if (errorList.errorCount() == 0){
    		
         		//passed leave overlap validation!  time to add leave
        		//update employee # of requested days left
        		//add the new leave to database of leaves
    			employeeService.postUpdateEmployee(emp.getEmployeeId(), emp.getLeaveBalance()- leave.getNumDays());
    			employeeService.postAddLeave(leave);
    		}
    	}
    	else
    		errorList.addError("Error: Employee does not have enough days left");
    	
    	return errorList;
    }
   
    /**
     * validateLeave
     * 
	 * Validates a leave object with the following business object
	 *  - start date and end date in future
	 *  - start date is before end date
	 *  - days of leave is equal to end date - start date
	 *  - leave type is "Earned Leave"
	 *  - leave reason is not empty
     * 
     * @param leave - the Leave object to be validated
     * @return a list of errors, blank if valid
     */
    public LeaveErrors validateLeave(Leave leave){
    	
    	LeaveErrors errorList = new LeaveErrors();
    	
    	if (leave != null){
    		System.out.println("Making sure that leave Request was accessed with leave object : \n\t" + leave);
    		
    		Calendar cal = Calendar.getInstance();
    		Date start = leave.getStartDate();
    		Date end = leave.getEndDate();
    		int numDays = leave.getNumDays();
    		String leaveType = leave.getLeaveType();
    		
    		//validation of date and num days
    		if (start != null && end != null){
    			if (start.before(cal.getTime()))
    				errorList.addError("Error: Start date must be after today");
    			if (end.before(cal.getTime())) 
    				errorList.addError("Error: End date must be after today");
    			if (end.before(start))
    				errorList.addError("Error: Start date must be before End date");
    			
    			//only need to validate num days when date is valid
    			if (errorList.errorCount() == 0){
    				
	    			long diffInMillies = Math.abs(end.getTime() - start.getTime());
	    		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
	    		    	//adding 1 because leave requests are inclusive in days
	    			if (numDays != diff){
	    				errorList.addError("Error: Number of leave days is incorrect, should be : " + diff);
	    			}
	    			else{	//all validation logic succeeded
	    				leave.setAppliedOn(cal.getTime());
	    				leave.setLeaveStatus("PENDING");
	    			}
    			}
    		}
    		else{
    			errorList.addError("Error: Date field(s) are empty");
    		}
    		
    		//validation of leave type
    		if (leaveType != null && !leaveType.isEmpty()){
    			if (!leaveType.equals("Earned Leave"))
    				errorList.addError("Error: Leave reason is invalid");
    		}
    		else{
    			errorList.addError("Error: Leave type is empty");
    		}
    			
    		//leave reason is allowed to be empty
		}
    	else{
			errorList.addError("Error: Leave object is null");
		}
    	return errorList;
    }
    
    
    /**
     * checkOverlap
     * 
     * Takes 2 Leave objects and checks if their date ranges overlap
     * checks inclusively for start/end date ranges
     * @param l1 - first leave to compare
     * @param l2 - second leave to compare
     * @return true or false whether overlapped or not
     */
    public boolean checkOverlap(Leave l1, Leave l2){
    	
    	Date start1 = l1.getStartDate();
    	Date start2 = l2.getStartDate();
    	Date end1 = l1.getEndDate();
    	Date end2 = l2.getEndDate();

    	Date lateStart = (start1.compareTo(start2) > 0) ? start1 : start2;
    	Date earlyEnd = (end1.compareTo(end2) < 0) ? end1 : end2;

    	return lateStart.compareTo(earlyEnd) < 0;
    }
}
