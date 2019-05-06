package seedu.address.testutil;

import seedu.address.model.FinanceTracker;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * A utility class containing a list of {@code Expense} objects, {@code Budget} objects, {@code Debt} objects,
 * and {@code Recurring} objects to be used in tests.
 */
public class TypicalFinanceTracker {

    private TypicalFinanceTracker() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical expenses, debts, budgets, recurrings.
     */
    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker ft = new FinanceTracker();
        for (Expense expense : TypicalExpenses.getTypicalExpenses()) {
            ft.addExpense(expense);
        }
        for (Budget budget : TypicalBudgets.getTypicalBudgets()) {
            ft.addBudget(budget);
        }
        for (Debt debt : TypicalDebts.getTypicalDebts()) {
            ft.addDebt(debt);
        }
        for (Recurring recurring : TypicalRecurrings.getTypicalRecurring()) {
            ft.addRecurring(recurring);
        }
        return ft;
    }
}
