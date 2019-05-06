package com.leave.dtos;

public class LeaveDetailsDTO extends EmployeeDetailsDTO{
	int leaveId;
	String startDate;
	String endDate;
	int days;
	String leaveType;
	String reason;
	String status;
	String managerCommnets;
	
	public LeaveDetailsDTO() {
		super();
	}


	public LeaveDetailsDTO(int leaveId, int employeeId, String employeeName, int leaveBalance, String startDate, String endDate,
			int days, String leaveType, String status) {
		super();
		this.leaveId = leaveId;
		this.setEmployeeId(employeeId);
		this.employeeName = employeeName;
		this.setLeaveBalance(leaveBalance);
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.leaveType = leaveType;
		this.status = status;
	}



	public LeaveDetailsDTO(int leaveId, int employeeId, String employeeName, int leaveBalance, String startDate, String endDate,
			int days, String leaveType, String reason, String status, String managerCommnets) {
		super();
		this.leaveId = leaveId;
		this.setEmployeeId(employeeId);
		this.employeeName = employeeName;
		this.setLeaveBalance(leaveBalance);
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.leaveType = leaveType;
		this.reason = reason;
		this.status = status;
		this.managerCommnets = managerCommnets;
	}

	
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



	public String getManagerCommnets() {
		return managerCommnets;
	}



	public void setManagerCommnets(String managerCommnets) {
		this.managerCommnets = managerCommnets;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + days;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + leaveId;
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + ((managerCommnets == null) ? 0 : managerCommnets.hashCode());
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
		if (leaveId != other.leaveId)
			return false;
		if (leaveType == null) {
			if (other.leaveType != null)
				return false;
		} else if (!leaveType.equals(other.leaveType))
			return false;
		if (managerCommnets == null) {
			if (other.managerCommnets != null)
				return false;
		} else if (!managerCommnets.equals(other.managerCommnets))
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
		return "LeaveDetailsDTO [leaveId=" + leaveId + ", startDate=" + startDate + ", endDate=" + endDate + ", days="
				+ days + ", leaveType=" + leaveType + ", reason=" + reason + ", status=" + status + ", managerCommnets="
				+ managerCommnets + ", employeeId=" + getEmployeeId() + ", employeeName=" + employeeName + ", leaveBalance="
				+ getLeaveBalance() + "]";
	}
	

}
