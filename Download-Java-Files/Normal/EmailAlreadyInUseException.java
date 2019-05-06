package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailAlreadyInUseException extends JuggerTrackerException {
    private final String email;

    public EmailAlreadyInUseException(String email) {
        super();
        this.email = email;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.EMAIL_ALREADY_IN_USE;
    }

    @Override
    public String getErrorMessage() {
        return "Email " + email + " is already in use.";
    }
}
