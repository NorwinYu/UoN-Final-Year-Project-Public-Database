package systemtests.expensesystemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalExpenses.KEYWORD_MATCHING_CHICKEN;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.ClearExpenseCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import systemtests.FinanceTrackerSystemTest;

public class ClearExpenseCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty finance tracker, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearExpenseCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing finance tracker -> original finance tracker restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing finance tracker -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in expense list and clears finance tracker -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        selectExpense(Index.fromOneBased(1));
        assertCommandSuccess(ClearExpenseCommand.COMMAND_WORD);
        assertSelectedCardDeselected();

        /* Case: filters the expense list before clearing -> entire finance tracker cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        showExpensesWithName(KEYWORD_MATCHING_CHICKEN);
        assertCommandSuccess(ClearExpenseCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: clear empty finance tracker -> cleared */
        assertCommandSuccess(ClearExpenseCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display box
     * displays {@code ClearExpenseCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearExpenseCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearExpenseCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
