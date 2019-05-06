package com.leave.dtos;

public class LeaveDetailsDTO extends EmployeeDetailsDTO{
	int leaveId;
	String startDate;
	String endDate;
	int days;
	String leaveType;
	String reason;
	String status;
	String managerComments;
	String acceptance;
	String appliedOn;
	
	
	
	public int getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}


	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



	public String getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}


	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	
	public String getAppliedOn() {
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	

	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}



	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}



	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}



	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getManagerComments() {
		return managerComments;
	}

	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + days;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((appliedOn == null) ? 0 : appliedOn.hashCode());
		result = prime * result + leaveId;
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + ((managerComments == null) ? 0 : managerComments.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveDetailsDTO other = (LeaveDetailsDTO) obj;
		if (days != other.days)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (appliedOn == null) {
			if (other.appliedOn != null)
				return false;
		} else if (!appliedOn.equals(other.appliedOn))
			return false;
		if (leaveId != other.leaveId)
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
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LeaveDetailsDTO [leaveId=" + leaveId+ ", startDate=" + startDate + ", endDate=" + endDate + ", days="
				+ days + ", leaveType=" + leaveType + ", reason=" + reason + ", status=" + status + ", managerCommnets="
				+ managerComments + ", employeeId=" + getEmployeeId() + ", employeeName=" + employeeName + ", leaveBalance="
				+ getLeaveBalance() + "]";
	}
	

}
