package edu.uw.edm.pws.exception;

import edu.uw.edm.pws.model.PWSError;
import lombok.Getter;

/**
 * @author Maxime Deravet Date: 2019-02-19
 */
public class BadPersonRequestException extends PWSException {

    public BadPersonRequestException(PWSError pwsError) {
        super(pwsError);

    }
}
