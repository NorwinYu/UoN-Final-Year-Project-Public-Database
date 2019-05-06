package seedu.address.logic.commands.expensecommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteExpenseCommand}.
 */
public class DeleteExpenseCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(deleteExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitFinanceTracker();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        Index outOfBoundIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getExpenseList().size());

        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> first expense deleted
        deleteExpenseCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered expense list to show all persons
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first expense deleted again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Expense} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted expense in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the expense object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());

        showExpenseAtIndex(model, INDEX_SECOND_EXPENSE);
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> deletes second expense in unfiltered expense list / first expense in filtered expense list
        deleteExpenseCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered expense list to show all persons
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(expenseToDelete, model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased()));
        // redo -> deletes same second expense in unfiltered expense list
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteExpenseCommand deleteFirstCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        DeleteExpenseCommand deleteSecondCommand = new DeleteExpenseCommand(INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExpenseCommand deleteFirstCommandCopy = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
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
        model.updateFilteredExpenseList(p -> false);

        assertTrue(model.getFilteredExpenseList().isEmpty());
    }
}
