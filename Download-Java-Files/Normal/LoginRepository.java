package com.leave.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.leave.obj.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
	Login findByUsername(String username);
}