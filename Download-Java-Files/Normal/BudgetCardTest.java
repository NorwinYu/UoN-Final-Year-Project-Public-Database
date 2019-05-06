package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBudget;

import org.junit.Test;

import guitests.guihandles.BudgetCardHandle;
import seedu.address.model.budget.Budget;
import seedu.address.testutil.BudgetBuilder;


public class BudgetCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no remarks
        Budget budgetWithNoRemarks = new BudgetBuilder().withRemarks("").build();
        BudgetCard budgetCard = new BudgetCard(budgetWithNoRemarks);
        uiPartRule.setUiPart(budgetCard);
        assertCardDisplay(budgetCard, budgetWithNoRemarks);

        // with remarks
        Budget budgetWithRemarks = new BudgetBuilder().build();
        budgetCard = new BudgetCard(budgetWithRemarks);
        uiPartRule.setUiPart(budgetCard);
        assertCardDisplay(budgetCard, budgetWithRemarks);
    }

    @Test
    public void equals() {
        Budget budget = new BudgetBuilder().build();
        BudgetCard budgetCard = new BudgetCard(budget);

        // same budget, same index -> returns true
        BudgetCard copy = new BudgetCard(budget);
        assertTrue(budgetCard.equals(copy));

        // same object -> returns true
        assertTrue(budgetCard.equals(budgetCard));

        // null -> returns false
        assertFalse(budgetCard.equals(null));

        // different types -> returns false
        assertFalse(budgetCard.equals(0));

        // different budget category -> returns false
        Budget differentBudget = new BudgetBuilder().withCategory("utilities").build();
        assertFalse(budgetCard.equals(new BudgetCard(differentBudget)));
    }

    /**
     * Asserts that {@code budgetCard} displays the details of {@code expectedBudget} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BudgetCard budgetCard, Budget expectedBudget) {
        guiRobot.pauseForHuman();

        BudgetCardHandle budgetCardHandle = new BudgetCardHandle(budgetCard.getRoot());

        // verify budget details are displayed correctly
        assertCardDisplaysBudget(expectedBudget, budgetCardHandle);
    }
}
