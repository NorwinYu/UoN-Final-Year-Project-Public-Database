package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import at.jugger.tracker.service.dto.LoginToken;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenExpiredException extends JuggerTrackerException {
    private final LoginToken loginToken;

    public TokenExpiredException(LoginToken loginToken) {
        super();
        this.loginToken = loginToken;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.GONE;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.TOKEN_EXPIRED;
    }

    @Override
    public String getErrorMessage() {
        return "Token " + loginToken.getToken() + " has expired on " + loginToken.getExpiryDate().toString();
    }
}
