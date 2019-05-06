package seedu.address.model.budget;

/**
 * Signals that operation will cause two budgets whose dates are overlapping.
 */
public class OverlappingBudgetException extends RuntimeException {

    public OverlappingBudgetException() {
        super("Operation would result in overlapping budgets");
    }
}
