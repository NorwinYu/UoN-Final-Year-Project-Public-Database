package com.bakercreations.contacts.contactmanager.controlleradvice;

import com.bakercreations.contacts.contactmanager.exceptions.InvalidContactException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidContactAdvice {
    @ResponseBody
	@ExceptionHandler(InvalidContactException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String employeeNotFoundHandler(InvalidContactException ex) {
		return ex.getMessage();
	}
}