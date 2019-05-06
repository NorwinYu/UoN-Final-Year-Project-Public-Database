package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import at.jugger.tracker.dto.User;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotTrackableException extends JuggerTrackerException {
    private final User user;

    public UserNotTrackableException(User user) {
        super();
        this.user = user;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.USER_NOT_TRACKABLE;
    }

    @Override
    public String getErrorMessage() {
        return "The user " + user.getFirstName() + " " + user.getLastName() + " (" + user.getId() + ") does not want to be tracked";
    }
}
