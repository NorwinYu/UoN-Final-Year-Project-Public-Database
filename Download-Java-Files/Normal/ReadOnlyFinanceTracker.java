package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFinanceTracker extends Observable {

    /**
     * Returns an unmodifiable view of the expense list.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Returns an unmodifiable view of the debt list.
     */
    ObservableList<Debt> getDebtList();

    /**
     * Returns as unmodifiable view of the budget list.
     */
    ObservableList<Budget> getBudgetList();

    /**
     * Returns an unmodifiable view of the recurring list.
     */
    ObservableList<Recurring> getRecurringList();

}
