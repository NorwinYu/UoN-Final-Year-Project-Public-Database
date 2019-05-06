package com.leave.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leave.dtos.LoginDTO;
import com.leave.obj.Login;
import com.leave.services.LoginService;;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	LoginService loginService;
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Login getEmployee(@RequestBody LoginDTO loginDTO) {

		Optional<Login> user = loginService.loginEmployee(loginDTO.getUsername(), loginDTO.getPassword());

		if (user != null && user.isPresent())
			return user.get();
		else
			return null;

	}

}
