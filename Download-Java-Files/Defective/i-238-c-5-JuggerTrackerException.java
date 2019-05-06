package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import org.springframework.http.HttpStatus;

public abstract class JuggerTrackerException extends RuntimeException {
    public JuggerTrackerException() {
        super();
    }

    public JuggerTrackerException(Throwable cause) {
        super(cause);
    }

    public abstract HttpStatus getHttpStatus();

    public abstract TrackerError.TypeEnum getErrorType();

    public abstract String getErrorMessage();

    public final TrackerError getError() {
        TrackerError error = new TrackerError();
        error.setType(getErrorType());
        error.setMessage(getErrorMessage());
        return error;
    }
}
