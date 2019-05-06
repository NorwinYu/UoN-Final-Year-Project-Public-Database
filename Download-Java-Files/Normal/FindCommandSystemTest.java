package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_FOUND_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.EXPENSE;
import static seedu.address.testutil.TypicalExpenses.GROCERIES;
import static seedu.address.testutil.TypicalExpenses.KEYWORD_MATCHING_CHICKEN;
import static seedu.address.testutil.TypicalExpenses.LAPTOP;
import static seedu.address.testutil.TypicalExpenses.TAXI;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.DeleteExpenseCommand;
import seedu.address.logic.commands.generalcommands.FindCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple expenses in finance tracker, command with leading spaces and trailing spaces
         * -> 1 expense found
         */
        String command = "   " + FindCommand.COMMAND_WORD + "  rice   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, EXPENSE, DUCK_RICE); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where expense list is displaying the expenses we are finding
         * -> 1 expense found
         */
        command = FindCommand.COMMAND_WORD + " rice";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find expense where expense list is not displaying the expense we are finding -> 1 expense found */
        command = FindCommand.COMMAND_WORD + " NTUC";
        ModelHelper.setFilteredList(expectedModel, GROCERIES);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple expenses in finance tracker, 2 keywords -> 2 expenses found */
        command = FindCommand.COMMAND_WORD + " taxi laptop";
        ModelHelper.setFilteredList(expectedModel, TAXI, LAPTOP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple expenses in finance tracker, 2 keywords in reversed order -> 2 expenses found */
        command = FindCommand.COMMAND_WORD + " laptop taxi";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple expenses in finance tracker, 2 keywords with 1 repeat -> 2 expenses found */
        command = FindCommand.COMMAND_WORD + " laptop taxi laptop";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple expenses in finance tracker, 2 matching keywords and 1 non-matching keyword
         * -> 2 expenses found
         */
        command = FindCommand.COMMAND_WORD + " laptop taxi NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same expenses in finance tracker after deleting 1 of them -> 1 expense found */
        executeCommand(DeleteExpenseCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getFinanceTracker().getExpenseList().contains(TAXI));
        command = FindCommand.COMMAND_WORD + " laptop taxi";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, LAPTOP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find expense in finance tracker, keyword is same as name but of different case -> 1 expense found */
        command = FindCommand.COMMAND_WORD + " rIcE";
        ModelHelper.setFilteredList(expectedModel, DUCK_RICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find expense in finance tracker, keyword is substring of name -> 0 found */
        command = FindCommand.COMMAND_WORD + " apto";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find expense in finance tracker, name is substring of keyword -> 0 found */
        command = FindCommand.COMMAND_WORD + " laptops";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find expense not in finance tracker -> 0 found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find amount number of expense in finance tracker -> 0 found */
        command = FindCommand.COMMAND_WORD + " " + LAPTOP.getAmount().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of expense in finance tracker -> 0 found */
        command = FindCommand.COMMAND_WORD + " " + LAPTOP.getDate().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find category of expense in finance tracker -> 0 found */
        command = FindCommand.COMMAND_WORD + " " + LAPTOP.getCategory().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find remarks of expense in finance tracker -> 0
         found */
        command = FindCommand.COMMAND_WORD + " " + LAPTOP.getRemarks();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while an expense is selected -> selected card deselected */
        showAllExpenses();
        selectExpense(Index.fromOneBased(1));
        assertFalse(getExpenseListPanel().getHandleToSelectedCard().getName().equals(LAPTOP.getName().name));
        command = FindCommand.COMMAND_WORD + " laptop";
        ModelHelper.setFilteredList(expectedModel, LAPTOP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find expense in empty finance tracker -> 0 expenses found */
        deleteAllExpenses();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CHICKEN;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, LAPTOP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EXPENSES_FOUND_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_EXPENSES_FOUND_OVERVIEW, expectedModel.getFilteredExpenseList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
