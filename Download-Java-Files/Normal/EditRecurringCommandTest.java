package seedu.address.logic.commands.recurringcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_RECURRING;
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
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.EditRecurringDescriptorBuilder;
import seedu.address.testutil.RecurringBuilder;

public class EditRecurringCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Recurring editedRecurring = new RecurringBuilder().build();
        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder(editedRecurring)
                .build();
        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(INDEX_FIRST_EXPENSE, descriptor);

        String expectedMessage = String.format(EditRecurringCommand.MESSAGE_EDIT_RECURRING_SUCCESS, editedRecurring);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setRecurring(model.getFilteredRecurringList().get(0), editedRecurring);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredRecurringList().size());
        Recurring lastRecurring = model.getFilteredRecurringList().get(indexLastPerson.getZeroBased());

        RecurringBuilder personInList = new RecurringBuilder(lastRecurring);
        Recurring editedRecurring = personInList.withName(VALID_NAME_RECURRING).withAmount(VALID_AMOUNT_RECURRING)
                .withRemarks(VALID_REMARKS_RECURRING).build();

        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder().withName(
                VALID_NAME_RECURRING).withAmount(VALID_AMOUNT_RECURRING).withRemarks(VALID_REMARKS_RECURRING).build();
        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditRecurringCommand.MESSAGE_EDIT_RECURRING_SUCCESS, editedRecurring);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setRecurring(lastRecurring, editedRecurring);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(INDEX_FIRST_EXPENSE,
                new EditRecurringCommand.EditRecurringDescriptor());
        Recurring editedRecurring = model.getFilteredRecurringList().get(INDEX_FIRST_EXPENSE.getZeroBased());

        String expectedMessage = String.format(EditRecurringCommand.MESSAGE_EDIT_RECURRING_SUCCESS, editedRecurring);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);

        Recurring recurringInFilteredList = model.getFilteredRecurringList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        Recurring editedRecurring = new RecurringBuilder(recurringInFilteredList).withName(VALID_NAME_RECURRING)
                .build();
        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(INDEX_FIRST_EXPENSE,
                new EditRecurringDescriptorBuilder().withName(VALID_NAME_RECURRING).build());

        String expectedMessage = String.format(EditRecurringCommand.MESSAGE_EDIT_RECURRING_SUCCESS, editedRecurring);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setRecurring(model.getFilteredRecurringList().get(0), editedRecurring);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editRecurringCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecurringList().size() + 1);
        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder()
                .withName(VALID_NAME_RECURRING).build();
        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editRecurringCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        Index outOfBoundIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getRecurringList().size());

        EditRecurringCommand editRecurringCommand = new EditRecurringCommand(outOfBoundIndex,
                new EditRecurringDescriptorBuilder().withName(VALID_NAME_RECURRING).build());

        assertCommandFailure(editRecurringCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditRecurringCommand standardCommand = new EditRecurringCommand(INDEX_FIRST_EXPENSE, DESC_RECURRING);

        // same values -> returns true
        EditRecurringCommand.EditRecurringDescriptor copyDescriptor =
                new EditRecurringCommand.EditRecurringDescriptor(DESC_RECURRING);
        EditRecurringCommand commandWithSameValues = new EditRecurringCommand(INDEX_FIRST_EXPENSE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditRecurringCommand(INDEX_SECOND_EXPENSE, DESC_RECURRING)));

        // different descriptor -> returns false
        EditRecurringCommand.EditRecurringDescriptor differentDescriptor = new EditRecurringCommand
                .EditRecurringDescriptor();
        assertFalse(standardCommand.equals(new EditRecurringCommand(INDEX_FIRST_EXPENSE, differentDescriptor)));
    }

}
