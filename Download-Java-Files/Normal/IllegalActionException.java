package recruitment.domain;

/**
 * Thrown whenever an attempt is made to perform an action that is
 * not allowed by the application's business rules.
 */
public class IllegalActionException extends Exception {

    /**
     * Creates new instance of with the specified message.
     *
     * @param msg A message explaining why the exception is thrown.
     */
    public IllegalActionException(String msg) {
        super(msg);
    }
}
