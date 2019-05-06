package my.example.rest_logic.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import my.example.rest_logic.errors.ProfileErrorResponse;
import my.example.rest_logic.errors.ProfileNotFoundException;

@ControllerAdvice
public class ProfileRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ProfileErrorResponse> profileNotFound(ProfileNotFoundException ex){
		ProfileErrorResponse errorResponse = new ProfileErrorResponse();
		
		//Build response
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(Instant.now().toString());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ProfileErrorResponse> profileNotFound(Exception ex){
		ProfileErrorResponse errorResponse = new ProfileErrorResponse();
		
		//Build response
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage("ERROR: Something is wrong with the request. Please check values and resend");
		errorResponse.setTimeStamp(Instant.now().toString());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
