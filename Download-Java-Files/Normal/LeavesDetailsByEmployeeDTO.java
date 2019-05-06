package com.leave.dtos;

import java.util.List;

public class LeavesDetailsByEmployeeDTO extends EmployeeDetailsDTO{
	List<LeaveDetailsDTO> leaves;

	public List<LeaveDetailsDTO> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<LeaveDetailsDTO> leaves) {
		this.leaves = leaves;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leaves == null) ? 0 : leaves.hashCode());
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
		LeavesDetailsByEmployeeDTO other = (LeavesDetailsByEmployeeDTO) obj;
		if (leaves == null) {
			if (other.leaves != null)
				return false;
		} else if (!leaves.equals(other.leaves))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LeavesDetailsByEmployeeDTO [leaves=" + leaves.toString() + ", employeeName=" + employeeName + "]";
	}
	
	

}
