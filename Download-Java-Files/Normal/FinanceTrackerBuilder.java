package seedu.address.testutil;

import seedu.address.model.FinanceTracker;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building finance tracker objects.
 * Example usage: <br>
 *     {@code FinanceTracker ft = new FinanceTrackerBuilder().withExpense("John", "Doe").build();}
 */
public class FinanceTrackerBuilder {

    private FinanceTracker financeTracker;

    public FinanceTrackerBuilder() {
        financeTracker = new FinanceTracker();
    }

    public FinanceTrackerBuilder(FinanceTracker financeTracker) {
        this.financeTracker = financeTracker;
    }

    /**
     * Adds a new {@code Expense} to the {@code FinanceTracker} that we are building.
     */
    public FinanceTrackerBuilder withExpense(Expense expense) {
        financeTracker.addExpense(expense);
        return this;
    }

    /**
     * Adds a new {@code Expense} to the {@code FinanceTracker} that we are building.
     */
    public FinanceTrackerBuilder withDebt(Debt debt) {
        financeTracker.addDebt(debt);
        return this;
    }

    public FinanceTracker build() {
        return financeTracker;
    }
}
