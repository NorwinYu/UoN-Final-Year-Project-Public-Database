package com.leave.obj;

import java.util.ArrayList;

public class LeaveErrors {

	private ArrayList<String> error;
	
	public LeaveErrors(){
		error = new ArrayList<String>();
	}
	public void addError(String toAdd){
		error.add(toAdd);
	}
	public void addAllErrors(LeaveErrors toAdd){
		error.addAll(toAdd.getErrors());
	}
	public ArrayList<String> getErrors(){
		return error;
	}
	public int errorCount(){
		return error.size();
	}
}
