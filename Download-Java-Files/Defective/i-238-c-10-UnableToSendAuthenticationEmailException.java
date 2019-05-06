package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import org.springframework.http.HttpStatus;

public class UnableToSendAuthenticationEmailException extends JuggerTrackerException {
    public UnableToSendAuthenticationEmailException(Exception exception) {
        super(exception);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.UNABLE_TO_SEND_AUTHENTICATION_EMAIL;
    }

    @Override
    public String getErrorMessage() {
        return "Unable to send authentication email: " + getCause().getMessage();
    }
}
