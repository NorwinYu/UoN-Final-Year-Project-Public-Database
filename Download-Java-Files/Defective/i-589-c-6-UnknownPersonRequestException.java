package edu.uw.edm.pws.exception;

/**
 * @author Maxime Deravet Date: 2019-02-19
 */
public class UnknownPersonRequestException extends PWSException {

    public UnknownPersonRequestException(String message, Exception e) {
        super(message, e);

    }
}
