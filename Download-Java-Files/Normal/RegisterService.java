package com.leave.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leave.obj.Login;
import com.leave.repositories.RegisterRepository;

@Service("RegisterService")
public class RegisterService {

	@Autowired
	RegisterRepository registerRepository;

	@Transactional
	public List<Login> getAllEmployees() {
		return registerRepository.findAll();
	}

	@Transactional
	public Login registerEmployee(Integer empId, String firstName, String lastName, String email,
		String username, String password) {
		Login loginEntity = new Login();

		loginEntity.setFirstName(firstName);
		loginEntity.setLastName(lastName);
		loginEntity.setEmail(email);
		loginEntity.setUsername(username);
		loginEntity.setPassword(password);
		return Optional.ofNullable(registerRepository.save(loginEntity)).orElseThrow(() -> new RuntimeException("Could not retrieve data from database"));


	}

}
