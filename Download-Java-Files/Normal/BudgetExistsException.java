package seedu.address.model.budget;

import static seedu.address.commons.core.Messages.MESSAGE_BUDGET_EXISTS;

/**
 * Signals that a budget already exists for the specific category.
 */
public class BudgetExistsException extends RuntimeException {

    public BudgetExistsException() {
        super(MESSAGE_BUDGET_EXISTS);
    }

}
