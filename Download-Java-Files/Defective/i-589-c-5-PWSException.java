package edu.uw.edm.pws.exception;

import edu.uw.edm.pws.model.PWSError;
import lombok.Getter;

/**
 * @author Maxime Deravet Date: 2019-02-14
 */
public class PWSException extends Throwable {
    @Getter
    private final PWSError pwsError;


    public PWSException(PWSError pwsError) {
        super(pwsError == null ? "Unknown exception" : pwsError.getStatusCode() + " - " + pwsError.getStatusDescription());
        this.pwsError = pwsError;
    }


    public PWSException(PWSError pwsError, String message) {
        super(message);
        this.pwsError = pwsError;
    }

    public PWSException(String message) {
        super(message);
        this.pwsError = new PWSError();
    }

    public PWSException(String message, Throwable cause) {
        super(message, cause);
        this.pwsError = new PWSError();
    }

    public PWSException(Throwable cause) {
        super(cause);
        this.pwsError = new PWSError();
    }
}
