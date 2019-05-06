package com.gcp.registration.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import com.gcp.registration.domain.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anuj Kumar
 * 
 *  this service is map the validation errors
 */

@Service
public class MapValidationErrorService {

	/**
	 * This method is used to map field validation messages
	 * 
	 * @param BindingResult
	 * @return ResponseEntity<?>
	 */
    public ResponseEntity<?> MapValidationService(BindingResult result){

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        return null;

    }
    
    /**
	 * This method is used to map field validation messages into errorMessage response field
	 * 
	 * @param BindingResult
	 * @return ResponseEntity<?>
	 */
    public ResponseEntity<?> mapValidationErrorMessage(BindingResult result){
    	Response response = new Response();
        if(result.hasErrors()){
            for(FieldError error: result.getFieldErrors()){
            	response.setErrorMessage(error.getDefaultMessage());
            }
            response.setMessage("Validations failed.");
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        return null;

    }
}
