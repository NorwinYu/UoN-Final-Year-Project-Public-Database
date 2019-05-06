package com.leave.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leave.obj.Leave;

import com.leave.obj.LeaveOne;

@Repository
public interface LeaveRepositoryOne extends JpaRepository<Leave, Integer>{
	@Query(value = "select * from leave_history" , nativeQuery = true)
	  //@Query(value = "select * from leave_history where emp_id = ?1" , nativeQuery = true)
	  List<LeaveOne> findLeaveByAttribute(int attribute);
}
