package at.jugger.tracker.controller.advice;

import at.jugger.tracker.exceptions.EmailAlreadyInUseException;
import at.jugger.tracker.exceptions.JuggerTrackerException;
import at.jugger.tracker.exceptions.UnhandledDataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JuggerTrackerException.class)
    protected ResponseEntity<Object> handleJuggerTrackerException(JuggerTrackerException exception, WebRequest request) {
        return processJuggerTrackerException(exception, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        Throwable cause = exception.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
            switch (constraintViolationException.getConstraintName()) {
                case "email":
                    return processJuggerTrackerException(new EmailAlreadyInUseException(""), request);
            }
        }

        UnhandledDataIntegrityViolationException unhandledDataIntegrityViolationException = new UnhandledDataIntegrityViolationException(exception);
        return processJuggerTrackerException(unhandledDataIntegrityViolationException, request);
    }

    private ResponseEntity<Object> processJuggerTrackerException(JuggerTrackerException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getError(),
                new HttpHeaders(), exception.getHttpStatus(), request);
    }
}
