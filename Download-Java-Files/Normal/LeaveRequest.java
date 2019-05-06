package com.leave.obj;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonAutoDetect
public class LeaveRequest {

	private int employee;
	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}
	
	private int leave;
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}

	@JsonDeserialize(using=DateDeserialize.class)
	private Date startDate;
	
	@JsonDeserialize(using=DateDeserialize.class)
	private Date endDate;
	private int numDays;
	private String leaveType;
	private String reasons;
	
	public LeaveRequest() {
		super();
	}

	public LeaveRequest(int leaveID, int employee, Date startDate, Date endDate, int numDays, String leaveType,
			String reasons) {
		super();
		this.leave = leaveID;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numDays = numDays;
		this.leaveType = leaveType;
		this.reasons = reasons;
	}
	

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getNumDays() {
		return numDays;
	}
	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employee;
		result = prime * result + leave;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + numDays;
		result = prime * result + ((reasons == null) ? 0 : reasons.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveRequest other = (LeaveRequest) obj;
		if (employee != other.employee)
			return false;
		if (leave != other.leave)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (leaveType == null) {
			if (other.leaveType != null)
				return false;
		} else if (!leaveType.equals(other.leaveType))
			return false;
		if (numDays != other.numDays)
			return false;
		if (reasons == null) {
			if (other.reasons != null)
				return false;
		} else if (!reasons.equals(other.reasons))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LeaveRequest [employee=" + employee + ", startDate=" + startDate + ", endDate=" + endDate + ", numDays="
				+ numDays + ", leaveType=" + leaveType + ", reasons=" + reasons + "]";
	}	
}
