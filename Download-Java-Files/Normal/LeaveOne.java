package com.leave.obj;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Proxy;


@Entity
@Table(name="leave_history",
	uniqueConstraints=@UniqueConstraint(columnNames={"leaveid"}))
@Proxy(lazy=false)
public class LeaveOne {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int leaveID;

	@Column(name = "emp_id")
	private int employee;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "number_of_days")
	private int numDays;
	
	@Column(name = "leave_type")
	private String leaveType;
	
	@Column(name = "reasons")
	private String reasons;
	
	@Column(name = "manager_comments")
	private String managerComments;
	
	@Column(name = "applied_on")
	private Date appliedOn;
	
	@Column(name = "leave_status")
	private String leaveStatus;
	
	public LeaveOne() {
		super();
	}

	public LeaveOne(int leaveID, int employee, Date startDate, Date endDate, int numDays, String leaveType,
			String reasons, String managerComments, Date appliedOn, String leaveStatus) {
		super();
		this.leaveID = leaveID;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numDays = numDays;
		this.leaveType = leaveType;
		this.reasons = reasons;
		this.managerComments = managerComments;
		this.appliedOn = appliedOn;
		this.leaveStatus = leaveStatus;
	}



	public int getLeaveID() {
		return leaveID;
	}
	public void setLeaveID(int leaveID) {
		this.leaveID = leaveID;
	}

	public int getEmployee() {
		return employee;
	}
	public void setEmployee(int employee) {
		this.employee = employee;
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
	public String getManagerComments() {
		return managerComments;
	}
	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}
	public Date getAppliedOn() {
		return appliedOn;
	}
	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}
	public String getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedOn == null) ? 0 : appliedOn.hashCode());
		result = prime * result + employee;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + leaveID;
		result = prime * result + ((leaveStatus == null) ? 0 : leaveStatus.hashCode());
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + ((managerComments == null) ? 0 : managerComments.hashCode());
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
		LeaveOne other = (LeaveOne) obj;
		if (appliedOn == null) {
			if (other.appliedOn != null)
				return false;
		} else if (!appliedOn.equals(other.appliedOn))
			return false;
		if (employee != other.employee) {
				return false;
		}
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (leaveID != other.leaveID)
			return false;
		if (leaveStatus == null) {
			if (other.leaveStatus != null)
				return false;
		} else if (!leaveStatus.equals(other.leaveStatus))
			return false;
		if (leaveType == null) {
			if (other.leaveType != null)
				return false;
		} else if (!leaveType.equals(other.leaveType))
			return false;
		if (managerComments == null) {
			if (other.managerComments != null)
				return false;
		} else if (!managerComments.equals(other.managerComments))
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
		return "Leave [leaveID=" + leaveID + ", employee=" + employee + ", startDate=" + startDate + ", endDate="
				+ endDate + ", numDays=" + numDays + ", leaveType=" + leaveType + ", reasons=" + reasons
				+ ", managerComments=" + managerComments + ", appliedOn=" + appliedOn + ", leaveStatus=" + leaveStatus
				+ "]";
	}
	
}
