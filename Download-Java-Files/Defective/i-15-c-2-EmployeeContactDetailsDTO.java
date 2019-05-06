package com.leave.dtos;

import java.util.Date;

public class EmployeeContactDetailsDTO extends EmployeeDetailsDTO{
	private long empPhone;
	private String empDept;
	private String empMail;
	private Date empDoj;
	private int leaveBalance;
	private int empMngId;
	public long getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(long empPhone) {
		this.empPhone = empPhone;
	}
	public String getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}
	public String getEmpMail() {
		return empMail;
	}
	public void setEmpMail(String empMail) {
		this.empMail = empMail;
	}
	public Date getEmpDoj() {
		return empDoj;
	}
	public void setEmpDoj(Date empDoj) {
		this.empDoj = empDoj;
	}
	public int getLeaveBalance() {
		return leaveBalance;
	}
	public void setLeaveBalance(int leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
	public int getEmpMngId() {
		return empMngId;
	}
	public void setEmpMngId(int empMngId) {
		this.empMngId = empMngId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((empDept == null) ? 0 : empDept.hashCode());
		result = prime * result + ((empDoj == null) ? 0 : empDoj.hashCode());
		result = prime * result + ((empMail == null) ? 0 : empMail.hashCode());
		result = prime * result + empMngId;
		result = prime * result + (int) (empPhone ^ (empPhone >>> 32));
		result = prime * result + leaveBalance;
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
		EmployeeContactDetailsDTO other = (EmployeeContactDetailsDTO) obj;
		if (empDept == null) {
			if (other.empDept != null)
				return false;
		} else if (!empDept.equals(other.empDept))
			return false;
		if (empDoj == null) {
			if (other.empDoj != null)
				return false;
		} else if (!empDoj.equals(other.empDoj))
			return false;
		if (empMail == null) {
			if (other.empMail != null)
				return false;
		} else if (!empMail.equals(other.empMail))
			return false;
		if (empMngId != other.empMngId)
			return false;
		if (empPhone != other.empPhone)
			return false;
		if (leaveBalance != other.leaveBalance)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EmployeeContactDetailsDTO [empPhone=" + empPhone + ", empDept=" + empDept + ", empMail=" + empMail
				+ ", empDoj=" + empDoj + ", leaveBalance=" + leaveBalance + ", empMngId=" + empMngId + ", employeeName="
				+ employeeName + "]";
	}
	
	
}
