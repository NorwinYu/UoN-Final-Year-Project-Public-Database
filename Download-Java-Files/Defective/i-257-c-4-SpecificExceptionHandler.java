package org.kaccag.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class SpecificExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(
            SpecificExceptionHandler.class.getSimpleName());

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e, WebRequest request) {
        LOGGER.warning("Error handler: mismatched arguments");
        DisplayedException error = new DisplayedException(
                HttpStatus.BAD_REQUEST,
                String.format("%s should be of type %s", e.getName(), e.getRequiredType())
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> processingPlotPoints(
            RuntimeException e, WebRequest request) {
        LOGGER.warning("Error handler: processing plot points");
        DisplayedException error = new DisplayedException(
                HttpStatus.BAD_REQUEST,
                e.getLocalizedMessage()
        );
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(
            ResourceNotFoundException e, WebRequest request) {
        LOGGER.warning("Error handler: resource not found");
        DisplayedException error = new DisplayedException(
                HttpStatus.NOT_FOUND,
                e.getLocalizedMessage()
        );
        return new ResponseEntity<>(error, error.getStatus());
    }
}
