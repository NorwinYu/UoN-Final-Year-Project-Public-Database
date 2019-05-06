package io.spring.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e; // Downcast to InvalidRequestExcption

        /* Note-: ire.getErrors() return object of class org.springframework.validation.Errors
           on which using getFieldErrors() returns java.util.List<org.springframework.validation.FieldError> and we streamed it
           using stream() method and using map to get list of FieldErrorResourse objects.
           Class org.springframework.validation.FieldError get error generting method from org.springframework.context.support.DefaultMessageSourceResolvable
           and both getCode() and getDefaultMessages() are @Nullable.
           But Since Spring Framework makes getCode() and getDefult() @Nullable I don't think this application would want that to be their Error Code and Message be null.
           And changed their type to @NonNull
        */
        List<FieldErrorResource> errorResources = ire.getErrors().getFieldErrors().stream().map(fieldError ->
            new FieldErrorResource(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage())).collect(Collectors.toList());

        ErrorResource error = new ErrorResource(errorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, UNPROCESSABLE_ENTITY, request);
    }
}
