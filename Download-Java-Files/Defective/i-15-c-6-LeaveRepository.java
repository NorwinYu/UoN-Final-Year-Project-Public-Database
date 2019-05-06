package com.leave.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leave.obj.Leave;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer>{
	
	  @Query(value = "select * from leave_history where t.emp_id = ?1" , nativeQuery = true)
	  List<Leave> findLeaveByAttribute(int attribute);
}
