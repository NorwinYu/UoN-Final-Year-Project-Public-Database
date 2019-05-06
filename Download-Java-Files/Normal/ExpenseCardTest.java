package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysExpense;

import org.junit.Test;

import guitests.guihandles.ExpenseCardHandle;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;


public class ExpenseCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no remarks
        Expense expenseWithNoRemarks = new ExpenseBuilder().withRemarks("").build();
        ExpenseCard expenseCard = new ExpenseCard(expenseWithNoRemarks, 1);
        uiPartRule.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, expenseWithNoRemarks, 1);

        // with remarks
        Expense expenseWithRemarks = new ExpenseBuilder().build();
        expenseCard = new ExpenseCard(expenseWithRemarks, 2);
        uiPartRule.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, expenseWithRemarks, 2);
    }

    @Test
    public void equals() {
        Expense expense = new ExpenseBuilder().build();
        ExpenseCard expenseCard = new ExpenseCard(expense, 0);

        // same expense, same index -> returns true
        ExpenseCard copy = new ExpenseCard(expense, 0);
        assertTrue(expenseCard.equals(copy));

        // same object -> returns true
        assertTrue(expenseCard.equals(expenseCard));

        // null -> returns false
        assertFalse(expenseCard.equals(null));

        // different types -> returns false
        assertFalse(expenseCard.equals(0));

        // different expense, same index -> returns false
        Expense differentExpense = new ExpenseBuilder().withName("differentName").build();
        assertFalse(expenseCard.equals(new ExpenseCard(differentExpense, 0)));

        // same expense, different index -> returns false
        assertFalse(expenseCard.equals(new ExpenseCard(expense, 1)));
    }

    /**
     * Asserts that {@code expenseCard} displays the details of {@code expectedExpense} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpenseCard expenseCard, Expense expectedExpense, int expectedId) {
        guiRobot.pauseForHuman();

        ExpenseCardHandle expenseCardHandle = new ExpenseCardHandle(expenseCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", expenseCardHandle.getId());

        // verify expense details are displayed correctly
        assertCardDisplaysExpense(expectedExpense, expenseCardHandle);
    }
}
