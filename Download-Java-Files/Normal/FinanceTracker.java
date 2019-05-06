package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.debt.Debt;
import seedu.address.model.debt.DebtList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseList;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.recurring.RecurringList;


/**
 * Wraps all data at the finance-tracker level
 */
public class FinanceTracker implements ReadOnlyFinanceTracker {

    private final ExpenseList expenses;
    private final DebtList debts;
    private final BudgetList budgets;
    private final RecurringList recurrings;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    public FinanceTracker() {
        expenses = new ExpenseList();
        debts = new DebtList();
        budgets = new BudgetList();
        recurrings = new RecurringList();
    }

    /**
     * Creates a FinanceTracker using the Expenses in the {@code toBeCopied}
     */
    public FinanceTracker(ReadOnlyFinanceTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations =================================================================

    /**
     * Resets the existing data of this {@code FinanceTracker} with {@code newData}.
     */
    public void resetData(ReadOnlyFinanceTracker newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenseList());
        setBudgets(newData.getBudgetList());
        setDebts(newData.getDebtList());
        setRecurrings(newData.getRecurringList());
    }

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
        indicateModified();
    }

    /**
     * Replaces the contents of the debt list with {@code debts}.
     */
    public void setDebts(List<Debt> debts) {
        this.debts.setDebts(debts);
        indicateModified();
    }

    /**
     * Replaces the contents of the budget list with {@code budgets}.
     */
    public void setBudgets(List<Budget> budgets) {
        this.budgets.setBudgets(budgets);
        indicateModified();
    }

    /**
     * Replaces the contents of the recurring list with {@code recurrings}.
     */
    public void setRecurrings(List<Recurring> recurrings) {
        this.recurrings.setRecurring(recurrings);
        indicateModified();
    }

    //// expense-level operations ==================================================================

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the
     * expense list in the finance tracker.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds a expense to the expense list in the finance tracker.
     */
    public void addExpense(Expense p) {
        expenses.add(p);
        indicateModified();
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the expense list in the finance tracker.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);
        expenses.setExpense(target, editedExpense);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the expense list in the finance tracker.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
        indicateModified();
    }

    //// debt-level operations =====================================================================

    /**
     * Returns true if a debt with the same identity as {@code debt} exists in the
     * debt list in the finance tracker.
     */
    public boolean hasDebt(Debt debt) {
        requireNonNull(debt);
        return debts.contains(debt);
    }

    /**
     * Adds a debt to the debt list in the finance tracker.
     */
    public void addDebt(Debt debt) {
        debts.add(debt);
        indicateModified();
    }

    /**
     * Replaces the given debt {@code target} in the list with {@code editedDebt}.
     * {@code target} must exist in the debt list in the finance tracker.
     */
    public void setDebt(Debt target, Debt editedDebt) {
        requireNonNull(editedDebt);

        debts.setDebt(target, editedDebt);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the debt list in the finance tracker.
     */
    public void removeDebt(Debt key) {
        debts.remove(key);
        indicateModified();
    }


    //// budget-level operations =====================================================================

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the
     * budget list in the finance tracker.
     */
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgets.contains(budget);
    }

    /**
     * Adds a budget to the budget list in the finance tracker.
     */
    public void addBudget(Budget budget) {
        budgets.addBudget(budget);
        indicateModified();
    }

    /**
     * Replaces the given budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the budget list in the finance tracker.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireNonNull(editedBudget);
        budgets.setBudget(target, editedBudget);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the budget list in the finance tracker.
     */
    public void removeBudget(Budget key) {
        budgets.removeBudget(key);
        indicateModified();
    }

    //// recurring-level operations =====================================================================

    /**
     * Returns true if a recurring with the same identity as {@code recurring} exists in the
     * recurring list in the finance tracker.
     */
    public boolean hasRecurring(Recurring recurring) {
        requireNonNull(recurring);
        return recurrings.contains(recurring);
    }

    /**
     * Adds a recurring to the recurring list in the finance tracker.
     */
    public void addRecurring(Recurring recurring) {
        recurrings.add(recurring);
        indicateModified();
    }

    /**
     * Replaces the given recurring {@code target} in the list with {@code editedRecurring}.
     * {@code target} must exist in the recurring list in the finance tracker.
     */
    public void setRecurring(Recurring target, Recurring editedRecurring) {
        requireNonNull(editedRecurring);

        recurrings.setRecurring(target, editedRecurring);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FinanceTracker}.
     * {@code key} must exist in the recurring list in the finance tracker.
     * */
    public void removeRecurring(Recurring key) {
        recurrings.remove(key);
        indicateModified();
    }

    //// listener methods ==========================================================================

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the finance tracker has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods ==========================================================================

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Debt> getDebtList() {
        return debts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    public ObservableList<Recurring> getRecurringList() {
        return recurrings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinanceTracker // instanceof handles nulls
                && expenses.equals(((FinanceTracker) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
