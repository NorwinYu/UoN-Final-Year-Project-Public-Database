package systemtests.expensesystemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.expensecommands.DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS;
import static seedu.address.testutil.TestUtil.getExpense;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalExpenses.KEYWORD_MATCHING_CHICKEN;
import static seedu.address.testutil.TypicalExpenses.KEYWORD_MATCHING_LAPTOP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.DeleteExpenseCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import systemtests.FinanceTrackerSystemTest;

public class DeleteExpenseCommandSystemTest extends FinanceTrackerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteExpenseCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first expense in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteExpenseCommand.COMMAND_WORD + "     " + INDEX_FIRST_EXPENSE.getOneBased()
                + "       ";
        Expense deletedExpense = removeExpense(expectedModel, INDEX_FIRST_EXPENSE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, deletedExpense);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last expense in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastExpenseIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastExpenseIndex);

        /* Case: undo deleting the last expense in the list -> last expense restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last expense in the list -> last expense deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeExpense(modelBeforeDeletingLast, lastExpenseIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle expense in the list -> deleted */
        Index middleExpenseIndex = getMidIndex(getModel());
        assertCommandSuccess(middleExpenseIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered expense list, delete index within bounds of address book and expense list -> deleted */
        showExpensesWithName(KEYWORD_MATCHING_LAPTOP);
        Index index = INDEX_FIRST_EXPENSE;
        assertTrue(index.getZeroBased() < getModel().getFilteredExpenseList().size());
        assertCommandSuccess(index);

        /* Case: filtered expense list, delete index within bounds of finance tracker but out of bounds of expense list
         * -> rejected
         */
        showExpensesWithName(KEYWORD_MATCHING_CHICKEN);
        int invalidIndex = getModel().getFinanceTracker().getExpenseList().size();
        command = DeleteExpenseCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while an expense card is selected ---------------------- */

        /* Case: delete the selected expense -> expense list panel selects the expense before the deleted expense */
        showAllExpenses();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectExpense(selectedIndex);
        command = DeleteExpenseCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedExpense = removeExpense(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, deletedExpense);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteExpenseCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteExpenseCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getFinanceTracker().getExpenseList().size() + 1);
        command = DeleteExpenseCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteExpenseCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteExpenseCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Expense} at the specified {@code index} in {@code model}'s finance tracker.
     * @return the removed expense
     */
    private Expense removeExpense(Model model, Index index) {
        Expense targetExpense = getExpense(model, index);
        model.deleteExpense(targetExpense);
        return targetExpense;
    }

    /**
     * Deletes the expense at {@code toDelete} by creating a default {@code DeleteExpenseCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteExpenseCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Expense deletedExpense = removeExpense(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, deletedExpense);

        assertCommandSuccess(
                DeleteExpenseCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteExpenseCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see FinanceTrackerSystemTest#assertSelectedExpenseCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedExpenseCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
