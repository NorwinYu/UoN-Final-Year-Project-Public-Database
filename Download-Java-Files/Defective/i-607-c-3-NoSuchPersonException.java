package edu.uw.edm.pws.exception;

import edu.uw.edm.pws.model.PWSError;

/**
 * @author Maxime Deravet Date: 2019-02-15
 */
public class NoSuchPersonException extends PWSException {

    public NoSuchPersonException(PWSError pwsError, String id) {
        super(pwsError, "Person " + id + " doesn't exist");
    }
}
