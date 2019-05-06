package seedu.address.model.recurring.exceptions;

/**
 * Signals that the operation is unable to be performed because it will result in a duplicate recurring.
 */
public class DuplicateRecurringException extends RuntimeException {
    public DuplicateRecurringException() {
        super("Operation would result in duplicate recurring");
    }
}
