package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRecurring;

import org.junit.Test;

import guitests.guihandles.RecurringCardHandle;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.RecurringBuilder;


public class RecurringCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no remarks
        Recurring recurringWithNoRemarks = new RecurringBuilder().withRemarks("").build();
        RecurringExpenseCard recurringCard = new RecurringExpenseCard(recurringWithNoRemarks, 1);
        uiPartRule.setUiPart(recurringCard);
        assertCardDisplay(recurringCard, recurringWithNoRemarks, 1);

        // with remarks
        Recurring recurringWithRemarks = new RecurringBuilder().build();
        recurringCard = new RecurringExpenseCard(recurringWithRemarks, 2);
        uiPartRule.setUiPart(recurringCard);
        assertCardDisplay(recurringCard, recurringWithRemarks, 2);
    }

    @Test
    public void equals() {
        Recurring recurring = new RecurringBuilder().build();
        RecurringExpenseCard recurringCard = new RecurringExpenseCard(recurring, 0);

        // same recurring, same index -> returns true
        RecurringExpenseCard copy = new RecurringExpenseCard(recurring, 0);
        assertTrue(recurringCard.equals(copy));

        // same object -> returns true
        assertTrue(recurringCard.equals(recurringCard));

        // null -> returns false
        assertFalse(recurringCard.equals(null));

        // different types -> returns false
        assertFalse(recurringCard.equals(0));

        // different recurring, same index -> returns false
        Recurring differentRecurring = new RecurringBuilder().withName("differentName").build();
        assertFalse(recurringCard.equals(new RecurringExpenseCard(differentRecurring, 0)));

        // same recurring, different index -> returns false
        assertFalse(recurringCard.equals(new RecurringExpenseCard(recurring, 1)));
    }

    /**
     * Asserts that {@code recurringCard} displays the details of {@code expectedRecurring} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RecurringExpenseCard recurringCard, Recurring expectedRecurring, int expectedId) {
        guiRobot.pauseForHuman();

        RecurringCardHandle recurringCardHandle = new RecurringCardHandle(recurringCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", recurringCardHandle.getId());

        // verify recurring details are displayed correctly
        assertCardDisplaysRecurring(expectedRecurring, recurringCardHandle);
    }
}
