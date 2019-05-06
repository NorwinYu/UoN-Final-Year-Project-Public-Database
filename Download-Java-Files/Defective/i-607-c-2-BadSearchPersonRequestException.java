package edu.uw.edm.pws.exception;

import edu.uw.edm.pws.model.PWSError;

/**
 * @author Maxime Deravet Date: 2019-02-15
 */
public class BadSearchPersonRequestException extends PWSException {

    public BadSearchPersonRequestException(PWSError pwsError) {
        super(pwsError);

    }
}
