package at.jugger.tracker.controller.advice;

import at.jugger.tracker.exceptions.JuggerTrackerException;
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
        return handleExceptionInternal(exception, exception.getError(),
                new HttpHeaders(), exception.getHttpStatus(), request);
    }
}
