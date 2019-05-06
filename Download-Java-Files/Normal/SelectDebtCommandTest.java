package seedu.address.logic.commands.debtcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDebtAtIndex;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_DEBT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SelectDebtCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredDebtList().size());

        assertExecutionSuccess(INDEX_FIRST_DEBT);
        assertExecutionSuccess(INDEX_THIRD_DEBT);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        showDebtAtIndex(expectedModel, INDEX_FIRST_DEBT);

        assertExecutionSuccess(INDEX_FIRST_DEBT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        showDebtAtIndex(expectedModel, INDEX_FIRST_DEBT);

        Index outOfBoundsIndex = INDEX_SECOND_DEBT;
        // ensures that outOfBoundIndex is still in bounds of finance tracker list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFinanceTracker().getDebtList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectDebtCommand selectFirstCommand = new SelectDebtCommand(INDEX_FIRST_DEBT);
        SelectDebtCommand selectSecondCommand = new SelectDebtCommand(INDEX_SECOND_DEBT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectDebtCommand selectFirstCommandCopy = new SelectDebtCommand(INDEX_FIRST_DEBT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different debt -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectDebtCommand} with the given {@code index},
     * and checks that the model's selected debt is set to the debt at {@code index} in the filtered debt list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectDebtCommand selectDebtCommand = new SelectDebtCommand(index);
        String expectedMessage = String.format(SelectDebtCommand.MESSAGE_SELECT_DEBT_SUCCESS, index.getOneBased());
        expectedModel.setSelectedDebt(model.getFilteredDebtList().get(index.getZeroBased()));

        assertCommandSuccess(selectDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectDebtCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectDebtCommand selectDebtCommand = new SelectDebtCommand(index);
        assertCommandFailure(selectDebtCommand, model, commandHistory, expectedMessage);
    }
}
