package com.leave.applyleave.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.leave.controller.LeaveAcceptanceController;
import com.leave.dtos.EmployeeDetailsDTO;
import com.leave.dtos.LeaveDetailsDTO;
import com.leave.obj.Leave;
import com.leave.obj.LeaveRequest;
import com.leave.obj.LeaveErrors;
import com.leave.services.EmployeeDataService;

@CrossOrigin(origins = "*")
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
    public LeaveErrors dbLeave(LeaveRequest leave){
    	
    	LeaveErrors errorList = new LeaveErrors();
    	LeaveDetailsDTO existingLeave = null;
    	//TODO if request fails
    	

		EmployeeDetailsDTO emp = (EmployeeDetailsDTO) employeeService.getEmployeeDataById(leave.getEmployee());
		List<Leave> leaves = employeeService.getLeaveData(emp.getEmployeeId());
		int emplDaysLeft = emp.getLeaveBalance();
		Calendar cal = Calendar.getInstance();
		
		Leave toUpdate = new Leave();
		toUpdate.setEndDate(leave.getEndDate());
		toUpdate.setLeaveType(leave.getLeaveType());
		toUpdate.setManagerComments("");
		toUpdate.setNumDays(leave.getNumDays());
		toUpdate.setReasons(leave.getReasons());
		toUpdate.setStartDate(leave.getStartDate());
		System.out.println("checking leave length " + leaves.size());
		
		if(leave.getLeave()>0){
    		//this is updating an EXISTING Leave
			System.out.println("existing leaveId"+leave.getLeave());
			//get days from EXISTING Leave
			existingLeave = (LeaveDetailsDTO) employeeService.getEmployeeData(leave.getLeave());
			System.out.println("existing days "+existingLeave.getDays()+" new days "+leave.getNumDays());
			//adjust days left so that the Existing leave (which will be edited) is not counted
			emplDaysLeft = emplDaysLeft - existingLeave.getDays();
    	}else{
    		//this is creating a NEW Leave
    	}
		//remaining tasks: dont include EXISTING Leave in Overlap, do an update and NOT an insert
		
		
		//make sure employee has enough days
    	if (leave.getNumDays() <= emplDaysLeft){
    		//make sure no overlaps between any of the leaves
    		leaves.forEach((toCheck) -> {
    			//if we are updating a leave, DO NOT check the EXISTING Leave
    			if(!(leave.getLeave()>0 && toCheck.getLeaveID()==leave.getLeave())){
    				System.out.println("checking leave " +toCheck.getLeaveID());
	    			System.out.println(((Leave) toCheck).getStartDate());
	    			System.out.println(((Leave) toCheck).getEndDate());
	    			System.out.println(((Leave) toUpdate).getStartDate());
	    			System.out.println(((Leave) toUpdate).getEndDate());
	    			if (checkOverlap(toCheck,toUpdate)){
	    				errorList.addError("Error: Leave overlaps with other leaves");
	    				return;
	    			}
    			}
    			
    			
			});
    		if (errorList.errorCount() == 0){
    			
    			
				
    			com.leave.obj.Employee empl = new com.leave.obj.Employee();
    			
				empl.setEmpId(emp.getEmployeeId());
				empl.setEmpName(emp.getEmployeeName());
				empl.setLeaveBalance(emp.getLeaveBalance());
				toUpdate.setEmployee(empl);
				if(leave.getLeave()>0){
					//get applied on, leave status, and set leaveID of EXISTING Leave
					toUpdate.setLeaveID(existingLeave.getLeaveId());
					toUpdate.setLeaveStatus(existingLeave.getStatus());
					try{
						DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
						Date date = format.parse(existingLeave.getAppliedOn());
						toUpdate.setAppliedOn(date);
					}catch(Exception e){
						
					}
				}else{
					//new entry
					toUpdate.setAppliedOn(cal.getTime());
					toUpdate.setLeaveStatus("PENDING");
				}
         		//passed leave overlap validation!  time to add leave
        		//add the new leave to database of leaves
    			employeeService.postAddLeave(toUpdate);
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
    public LeaveErrors validateLeave(LeaveRequest leave){
    	
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
    			}
    		}
    		else{
    			errorList.addError("Error: Date field(s) are empty");
    		}
    		
    		//validation of leave type
    		if (leaveType != null && !leaveType.isEmpty()){
    			if (!leaveType.equals("Earned leave/Privileged leave"))
    				errorList.addError("Error: Leave type is invalid");
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

    	return lateStart.compareTo(earlyEnd) <= 0;
    }
}
