package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.List;

import guitests.guihandles.BudgetCardHandle;
import guitests.guihandles.BudgetListPanelHandle;
import guitests.guihandles.DebtCardHandle;
import guitests.guihandles.DebtListPanelHandle;
import guitests.guihandles.ExpenseCardHandle;
import guitests.guihandles.ExpenseListPanelHandle;
import guitests.guihandles.RecurringCardHandle;
import guitests.guihandles.RecurringListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ExpenseCardHandle expectedCard, ExpenseCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(BudgetCardHandle expectedCard, BudgetCardHandle actualCard) {
        assertEquals(expectedCard.getSpent(), actualCard.getSpent());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDuration(), actualCard.getDuration());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(DebtCardHandle expectedCard, DebtCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDueDate(), actualCard.getDueDate());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RecurringCardHandle expectedCard, RecurringCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedExpense}.
     */
    public static void assertCardDisplaysExpense(Expense expectedExpense, ExpenseCardHandle actualCard) {
        assertEquals(expectedExpense.getName().name, actualCard.getName());
        assertEquals("$" + expectedExpense.getAmount(), actualCard.getAmount());
        assertEquals(expectedExpense.getCategory().toString(), actualCard.getCategory());
        assertEquals(expectedExpense.getDate().toString(), actualCard.getDate());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecurring}.
     */
    public static void assertCardDisplaysRecurring(Recurring expectedRecurring, RecurringCardHandle actualCard) {
        assertEquals(expectedRecurring.getName().name, actualCard.getName());
        assertEquals("$" + expectedRecurring.getAmount(), actualCard.getAmount());
        assertEquals(expectedRecurring.getCategory().toString(), actualCard.getCategory());
        assertEquals(expectedRecurring.getDate().toString(), actualCard.getDate());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBudget}.
     */
    public static void assertCardDisplaysBudget(Budget expectedBudget, BudgetCardHandle actualCard) {
        assertEquals("$" + expectedBudget.getAmount(), actualCard.getAmount());
        assertEquals(expectedBudget.getCategory().toString(), actualCard.getCategory());
        assertEquals(expectedBudget.getDuration(), actualCard.getDuration());
        DecimalFormat totalSpentFormat = new DecimalFormat("0.00");
        assertEquals("$" + totalSpentFormat.format((double) expectedBudget.getTotalSpent() / 100) + " ("
                + expectedBudget.getPercentage() + "%) spent", actualCard.getSpent());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedDebt}.
     */
    public static void assertCardDisplaysDebt(Debt expectedDebt, DebtCardHandle actualCard) {
        assertEquals("$" + expectedDebt.getAmount(), actualCard.getAmount());
        assertEquals(expectedDebt.getCategory().toString(), actualCard.getCategory());
        assertEquals(expectedDebt.getPersonOwed().name, actualCard.getName());
        assertEquals(expectedDebt.getDeadline().toString(), actualCard.getDueDate());
    }

    /**
     * Asserts that the list in {@code expenseListPanelHandle} displays the details of {@code expenses} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, Expense... expenses) {
        for (int i = 0; i < expenses.length; i++) {
            expenseListPanelHandle.navigateToCard(i);
            assertCardDisplaysExpense(expenses[i], expenseListPanelHandle.getExpenseCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code budgetListPanelHandle} displays the details of {@code budgets} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BudgetListPanelHandle budgetListPanelHandle, Budget... budgets) {
        for (int i = 0; i < budgets.length; i++) {
            budgetListPanelHandle.navigateToCard(i);
            assertCardDisplaysBudget(budgets[i], budgetListPanelHandle.getBudgetCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code debtListPanelHandle} displays the details of {@code debts} correctly and
     * in the correct order.
     */
    public static void assertListMatching(DebtListPanelHandle debtListPanelHandle, Debt... debts) {
        for (int i = 0; i < debts.length; i++) {
            debtListPanelHandle.navigateToCard(i);
            assertCardDisplaysDebt(debts[i], debtListPanelHandle.getDebtCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code recurringListPanelHandle} displays the details of {@code recurrings} correctly
     * and in the correct order.
     */
    public static void assertListMatching(RecurringListPanelHandle recurringListPanelHandle, Recurring... recurrings) {
        for (int i = 0; i < recurrings.length; i++) {
            recurringListPanelHandle.navigateToCard(i);
            assertCardDisplaysRecurring(recurrings[i], recurringListPanelHandle.getRecurringCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code expenseListPanelHandle} displays the details of {@code expenses} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, List<Expense> expenses) {
        assertListMatching(expenseListPanelHandle, expenses.toArray(new Expense[0]));
    }

    /**
     * Asserts that the list in {@code budgetListPanelHandle} displays the details of {@code budgets} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BudgetListPanelHandle budgetListPanelHandle, List<Budget> budgets) {
        assertListMatching(budgetListPanelHandle, budgets.toArray(new Budget[0]));
    }

    /**
     * Asserts that the list in {@code debtListPanelHandle} displays the details of {@code debts} correctly and
     * in the correct order.
     */
    public static void assertListMatching(DebtListPanelHandle debtListPanelHandle, List<Debt> debts) {
        assertListMatching(debtListPanelHandle, debts.toArray(new Debt[0]));
    }

    /**
     * Asserts that the list in {@code recurringListPanelHandle} displays the details of {@code recurrings} correctly
     * and in the correct order.
     */
    public static void assertListMatching(RecurringListPanelHandle recurringListPanelHandle,
                                          List<Recurring> recurrings) {
        assertListMatching(recurringListPanelHandle, recurrings.toArray(new Recurring[0]));
    }

    /**
     * Asserts the size of the list in {@code expenseListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ExpenseListPanelHandle expenseListPanelHandle, int size) {
        int numberOfExpenses = expenseListPanelHandle.getListSize();
        assertEquals(size, numberOfExpenses);
    }

    /**
     * Asserts the size of the list in {@code budgetListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(BudgetListPanelHandle budgetListPanelHandle, int size) {
        int numberOfBudgets = budgetListPanelHandle.getListSize();
        assertEquals(size, numberOfBudgets);
    }

    /**
     * Asserts the size of the list in {@code debtListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(DebtListPanelHandle debtListPanelHandle, int size) {
        int numberOfDebts = debtListPanelHandle.getListSize();
        assertEquals(size, numberOfDebts);
    }

    /**
     * Asserts the size of the list in {@code recurringListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RecurringListPanelHandle recurringListPanelHandle, int size) {
        int numberOfRecurrings = recurringListPanelHandle.getListSize();
        assertEquals(size, numberOfRecurrings);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
