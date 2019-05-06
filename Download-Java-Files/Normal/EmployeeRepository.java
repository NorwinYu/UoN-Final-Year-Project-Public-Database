package com.leave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leave.obj.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
