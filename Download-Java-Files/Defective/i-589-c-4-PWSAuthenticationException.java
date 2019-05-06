package edu.uw.edm.pws.exception;

import edu.uw.edm.pws.model.PWSError;

/**
 * @author Maxime Deravet Date: 2019-02-19
 */
public class PWSAuthenticationException extends PWSException {

    public PWSAuthenticationException(PWSError pwsError) {
        super(pwsError);

    }
}
