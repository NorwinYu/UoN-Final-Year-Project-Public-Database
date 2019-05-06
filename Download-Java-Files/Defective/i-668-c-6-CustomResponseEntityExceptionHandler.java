package com.gcp.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gcp.registration.domain.Response;

/**
 * @author Anuj Kumar
 * 
 * Custom response entity exception handler to handle to logonId exception
 */
@RestController
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * Function to check the logonId if already exists.
	 * 
	 * @param LogonIdException
	 * @param webRequest
	 * @return ResponseEntity<Object>
	 */
/*	@ExceptionHandler
	public ResponseEntity<Object> handleLogonIdException(LogonIdException ex, WebRequest webRequest){
		CustomUserExceptionResponse expResponse = new CustomUserExceptionResponse(ex.getMessage());
		return new ResponseEntity<>(expResponse, HttpStatus.BAD_REQUEST);
	}*/
	
	
	/**
	 * Function to check the logonId if already exists.
	 * 
	 * @param LogonIdException
	 * @param webRequest
	 * @return ResponseEntity<Object>
	 */
	@ExceptionHandler
	public ResponseEntity<Response> handleLogonIdException(LogonIdException ex, WebRequest webRequest){
		Response resp= new Response();
		resp.setErrorMessage(ex.getMessage());
		resp.setMessage("User resgistration failed.");
		return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
	}
}
