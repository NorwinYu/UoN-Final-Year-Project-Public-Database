package seedu.address.logic.commands.expensecommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectExpenseCommand}.
 */
public class SelectExpenseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredExpenseList().size());

        assertExecutionSuccess(INDEX_FIRST_EXPENSE);
        assertExecutionSuccess(INDEX_THIRD_EXPENSE);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        showExpenseAtIndex(expectedModel, INDEX_FIRST_EXPENSE);

        assertExecutionSuccess(INDEX_FIRST_EXPENSE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        showExpenseAtIndex(expectedModel, INDEX_FIRST_EXPENSE);

        Index outOfBoundsIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFinanceTracker().getExpenseList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectExpenseCommand selectFirstCommand = new SelectExpenseCommand(INDEX_FIRST_EXPENSE);
        SelectExpenseCommand selectSecondCommand = new SelectExpenseCommand(INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectExpenseCommand selectFirstCommandCopy = new SelectExpenseCommand(INDEX_FIRST_EXPENSE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectExpenseCommand} with the given {@code index},
     * and checks that the model's selected expense is set to the expense at {@code index} in the filtered expense list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectExpenseCommand selectExpenseCommand = new SelectExpenseCommand(index);
        String expectedMessage = String.format(SelectExpenseCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());
        expectedModel.setSelectedExpense(model.getFilteredExpenseList().get(index.getZeroBased()));

        assertCommandSuccess(selectExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectExpenseCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectExpenseCommand selectExpenseCommand = new SelectExpenseCommand(index);
        assertCommandFailure(selectExpenseCommand, model, commandHistory, expectedMessage);
    }
}
