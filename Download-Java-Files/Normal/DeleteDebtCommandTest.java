package seedu.address.logic.commands.debtcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDebtAtIndex;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEBT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.debt.Debt;

/**
 * Only contain integration test for interaction with the Model.
 * Includes integration tests for UndoCommand and RedoCommand for now.
 * Only contain unit tests for {@code DeleteDebtCommand} for now.
 */
public class DeleteDebtCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(INDEX_FIRST_DEBT);

        String expectedMessage = String.format(DeleteDebtCommand.MESSAGE_DELETE_DEBT_SUCCESS, debtToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(deleteDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(outOfBoundIndex);

        assertCommandFailure(deleteDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);

        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(INDEX_FIRST_DEBT);

        String expectedMessage = String.format(DeleteDebtCommand.MESSAGE_DELETE_DEBT_SUCCESS, debtToDelete);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.commitFinanceTracker();
        showNoDebt(expectedModel);

        assertCommandSuccess(deleteDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);

        Index outOfBoundIndex = INDEX_SECOND_DEBT;
        // ensures that outOfBoundIndex is still in bounds of finance tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getDebtList().size());

        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(outOfBoundIndex);

        assertCommandFailure(deleteDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(INDEX_FIRST_DEBT);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> first debt deleted
        deleteDebtCommand.execute(model, commandHistory);

        // undo -> reverts financetracker back to previous state and filtered debt list to show all persons
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first debt deleted again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(outOfBoundIndex);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(deleteDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Debt} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted debt in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the debt object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameDebtDeleted() throws Exception {
        DeleteDebtCommand deleteDebtCommand = new DeleteDebtCommand(INDEX_FIRST_DEBT);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());

        showDebtAtIndex(model, INDEX_SECOND_DEBT);
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> deletes second debt in unfiltered expense list / first debt in filtered debt list
        deleteDebtCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered debt list to show all debts
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(debtToDelete, model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased()));
        // redo -> deletes same second debt in unfiltered debt list
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteDebtCommand deleteFirstCommand = new DeleteDebtCommand(INDEX_FIRST_DEBT);
        DeleteDebtCommand deleteSecondCommand = new DeleteDebtCommand(INDEX_SECOND_DEBT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDebtCommand deleteFirstCommandCopy = new DeleteDebtCommand(INDEX_FIRST_DEBT);
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
    private void showNoDebt(Model model) {
        model.updateFilteredDebtList(p -> false);

        assertTrue(model.getFilteredDebtList().isEmpty());
    }
}
