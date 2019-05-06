package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TrainingNotFoundException extends JuggerTrackerException {
    private final String field;
    private final String value;

    public TrainingNotFoundException(String field, String value) {
        super();
        this.field = field;
        this.value = value;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.TRAINING_NOT_FOUND;
    }

    @Override
    public String getErrorMessage() {
        return "Could not find training with " + field + " " + value;
    }
}
