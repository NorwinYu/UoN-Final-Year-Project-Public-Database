package seedu.address.logic.commands.recurringcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecurringAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.testutil.TypicalRecurrings.getTypicalFinanceTrackerWithRecurrings;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recurring.Recurring;


public class DeleteRecurringCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Recurring recurringToDelete = model.getFilteredRecurringList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteRecurringCommand deleteRecurringCommand = new DeleteRecurringCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteRecurringCommand.MESSAGE_DELETE_RECURRING_SUCCESS,
                recurringToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteRecurring(recurringToDelete);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(deleteRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecurringList().size() + 1);
        DeleteRecurringCommand deleteRecurringCommand = new DeleteRecurringCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurringCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);

        Recurring recurringToDelete = model.getFilteredRecurringList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteRecurringCommand deleteRecurringCommand = new DeleteRecurringCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteRecurringCommand.MESSAGE_DELETE_RECURRING_SUCCESS,
                recurringToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteRecurring(recurringToDelete);
        expectedModel.commitFinanceTracker();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);

        Index outOfBoundIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getRecurringList().size());

        DeleteRecurringCommand deleteRecurringCommand = new DeleteRecurringCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurringCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRecurringCommand deleteFirstCommand = new DeleteRecurringCommand(INDEX_FIRST_EXPENSE);
        DeleteRecurringCommand deleteSecondCommand = new DeleteRecurringCommand(INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecurringCommand deleteFirstCommandCopy = new DeleteRecurringCommand(INDEX_FIRST_EXPENSE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredRecurringList(p -> false);

        assertTrue(model.getFilteredRecurringList().isEmpty());
    }
}
