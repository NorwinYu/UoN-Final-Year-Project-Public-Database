package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_RECURRING;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalFinanceTracker.getTypicalFinanceTracker;
import static seedu.address.testutil.TypicalRecurrings.PHONE_BILL;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.BudgetBuilder;
import seedu.address.testutil.DebtBuilder;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.RecurringBuilder;

public class FinanceTrackerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTracker financeTracker = new FinanceTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), financeTracker.getExpenseList());
        assertEquals(Collections.emptyList(), financeTracker.getDebtList());
        assertEquals(Collections.emptyList(), financeTracker.getBudgetList());
        assertEquals(Collections.emptyList(), financeTracker.getRecurringList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFinanceTracker_replacesData() {
        FinanceTracker newData = getTypicalFinanceTracker();
        financeTracker.resetData(newData);
        assertEquals(newData, financeTracker);
    }

    //// expense-related tests ==============================================================================

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasExpense(null);
    }

    @Test
    public void hasExpense_expenseNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasExpense_expenseInFinanceTracker_returnsTrue() {
        financeTracker.addExpense(DUCK_RICE);
        assertTrue(financeTracker.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasExpense_expenseWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addExpense(DUCK_RICE);
        Expense editedExpense = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_EXPENSE)
                .withRemarks(VALID_REMARKS_EXPENSE).build();
        assertTrue(financeTracker.hasExpense(editedExpense));
    }

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getExpenseList().remove(0);
    }

    //// debt-related tests ==============================================================================

    @Test
    public void hasDebt_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasDebt(null);
    }

    @Test
    public void hasDebt_debtNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasDebt(AMY));
    }

    @Test
    public void hasDebt_debtInFinanceTracker_returnsTrue() {
        financeTracker.addDebt(AMY);
        assertTrue(financeTracker.hasDebt(AMY));
    }

    @Test
    public void hasDebt_debtWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addDebt(AMY);
        Debt editedDebt = new DebtBuilder(AMY).withCategory(VALID_CATEGORY_DEBT)
                .withRemarks(VALID_REMARKS_DEBT).withDeadline(VALID_DEADLINE_DEBT).build();
        assertTrue(financeTracker.hasDebt(editedDebt));
    }

    @Test
    public void getDebtList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getDebtList().remove(0);
    }

    //// budget-related tests ==============================================================================

    @Test
    public void hasBudget_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasBudget(null);
    }

    @Test
    public void hasBudget_budgetNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasBudget(FOOD_BUDGET));
    }

    @Test
    public void hasBudget_budgetInFinanceTracker_returnsTrue() {
        financeTracker.addBudget(FOOD_BUDGET);
        assertTrue(financeTracker.hasBudget(FOOD_BUDGET));
    }

    @Test
    public void hasBudget_budgetWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addBudget(FOOD_BUDGET);
        Budget newBudget = new BudgetBuilder(FOOD_BUDGET).build();
        assertTrue(financeTracker.hasBudget(newBudget));
    }

    @Test
    public void getBudgetList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getBudgetList().remove(0);
    }

    //// recurring-related tests ==============================================================================

    @Test
    public void hasRecurring_nullRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        financeTracker.hasRecurring(null);
    }

    @Test
    public void hasRecurring_recurringNotInFinanceTracker_returnsFalse() {
        assertFalse(financeTracker.hasRecurring(PHONE_BILL));
    }

    @Test
    public void hasRecurring_recurringInFinanceTracker_returnsTrue() {
        financeTracker.addRecurring(PHONE_BILL);
        assertTrue(financeTracker.hasRecurring(PHONE_BILL));
    }

    @Test
    public void hasRecurring_recurringWithSameIdentityFieldsInFinanceTracker_returnsTrue() {
        financeTracker.addRecurring(PHONE_BILL);
        Recurring editedRecurring = new RecurringBuilder(PHONE_BILL).withCategory(VALID_CATEGORY_RECURRING)
                .withRemarks(VALID_REMARKS_RECURRING).build();
        assertTrue(financeTracker.hasRecurring(editedRecurring));
    }

    @Test
    public void getRecurringList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        financeTracker.getRecurringList().remove(0);
    }

    //// listener-related tests ==============================================================================

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.addExpense(DUCK_RICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        financeTracker.addListener(listener);
        financeTracker.removeListener(listener);
        financeTracker.addExpense(DUCK_RICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFinanceTracker whose expenses list can violate interface constraints.
     */
    private static class FinanceTrackerStub implements ReadOnlyFinanceTracker {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Debt> debts = FXCollections.observableArrayList();
        private final ObservableList<Budget> budgets = FXCollections.observableArrayList();
        private final ObservableList<Recurring> recurrings = FXCollections.observableArrayList();

        FinanceTrackerStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public ObservableList<Debt> getDebtList() {
            return debts;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public ObservableList<Recurring> getRecurringList() {
            return recurrings;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
