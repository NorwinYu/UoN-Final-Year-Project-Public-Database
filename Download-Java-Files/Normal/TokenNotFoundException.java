package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenNotFoundException extends JuggerTrackerException {
    private final String token;

    public TokenNotFoundException(String token) {
        super();
        this.token = token;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.TOKEN_NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "Token " + token + " not found";
    }
}
