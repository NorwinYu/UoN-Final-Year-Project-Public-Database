package com.bakercreations.contacts.contactmanager.controlleradvice;

import com.bakercreations.contacts.contactmanager.exceptions.ContactNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContactNotFoundAdvice {
    @ResponseBody
	@ExceptionHandler(ContactNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(ContactNotFoundException ex) {
		return ex.getMessage();
	}
}