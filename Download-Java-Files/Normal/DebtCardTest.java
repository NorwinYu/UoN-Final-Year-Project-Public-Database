package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDebt;

import org.junit.Test;

import guitests.guihandles.DebtCardHandle;
import seedu.address.model.debt.Debt;
import seedu.address.testutil.DebtBuilder;


public class DebtCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no remarks
        Debt debtWithNoRemarks = new DebtBuilder().withRemarks("").build();
        DebtCard debtCard = new DebtCard(debtWithNoRemarks, 1);
        uiPartRule.setUiPart(debtCard);
        assertCardDisplay(debtCard, debtWithNoRemarks, 1);

        // with remarks
        Debt debtWithRemarks = new DebtBuilder().build();
        debtCard = new DebtCard(debtWithRemarks, 2);
        uiPartRule.setUiPart(debtCard);
        assertCardDisplay(debtCard, debtWithRemarks, 2);
    }

    @Test
    public void equals() {
        Debt debt = new DebtBuilder().build();
        DebtCard debtCard = new DebtCard(debt, 0);

        // same debt, same index -> returns true
        DebtCard copy = new DebtCard(debt, 0);
        assertTrue(debtCard.equals(copy));

        // same object -> returns true
        assertTrue(debtCard.equals(debtCard));

        // null -> returns false
        assertFalse(debtCard.equals(null));

        // different types -> returns false
        assertFalse(debtCard.equals(0));

        // different debt, same index -> returns false
        Debt differentDebt = new DebtBuilder().withPersonOwed("differentName").build();
        assertFalse(debtCard.equals(new DebtCard(differentDebt, 0)));

        // same debt, different index -> returns false
        assertFalse(debtCard.equals(new DebtCard(debt, 1)));
    }

    /**
     * Asserts that {@code debtCard} displays the details of {@code expectedDebt} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DebtCard debtCard, Debt expectedDebt, int expectedId) {
        guiRobot.pauseForHuman();

        DebtCardHandle debtCardHandle = new DebtCardHandle(debtCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", debtCardHandle.getId());

        // verify debt details are displayed correctly
        assertCardDisplaysDebt(expectedDebt, debtCardHandle);
    }
}
