package at.jugger.tracker.exceptions;

import at.jugger.tracker.dto.TrackerError;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
public class UnhandledDataIntegrityViolationException extends JuggerTrackerException {
    private final DataIntegrityViolationException dataIntegrityViolationException;

    public UnhandledDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        super();
        this.dataIntegrityViolationException = dataIntegrityViolationException;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public TrackerError.TypeEnum getErrorType() {
        return TrackerError.TypeEnum.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getErrorMessage() {
        StringWriter sw = new StringWriter();
        dataIntegrityViolationException.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        String contraintName = "<unknown constraint name>";
        Throwable cause = dataIntegrityViolationException.getCause();
        if (cause instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
            contraintName = constraintViolationException.getConstraintName();
        }

        return "Unhandled data integrity violation exception. Violated constraint: " + contraintName + "\n" + dataIntegrityViolationException.getMessage() + "\n" + exceptionAsString;
    }
}
