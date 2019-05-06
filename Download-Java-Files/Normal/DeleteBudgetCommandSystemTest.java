package systemtests.budgetsystemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS;
import static seedu.address.model.attributes.Category.TRAVEL;
import static seedu.address.testutil.TestUtil.getBudget;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;
import systemtests.FinanceTrackerSystemTest;

public class DeleteBudgetCommandSystemTest extends FinanceTrackerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the travel budget in the list, command with leading spaces and trailing spaces -> deleted */
        Category category = TRAVEL;
        Model expectedModel = getModel();
        Model modelBeforeDeletingTravel = getModel();
        String command = "     " + DeleteBudgetCommand.COMMAND_WORD + "     " + CATEGORY_DESC_BUDGET + "       ";
        Budget deletedBudget = removeBudget(expectedModel, category);
        String expectedResultMessage = String.format(MESSAGE_DELETE_BUDGET_SUCCESS, deletedBudget);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the travel budget in the list -> travel budget restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingTravel, expectedResultMessage);

        /* Case: redo deleting the travel budget in the list -> travel budget deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeBudget(modelBeforeDeletingTravel, category);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingTravel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid category -> rejected */
        assertCommandFailure(DeleteBudgetCommand.COMMAND_WORD + INVALID_CATEGORY_DESC,
                Category.MESSAGE_CONSTRAINTS);

        /* Case: invalid category (budget does not exist) -> rejected */
        assertCommandFailure(DeleteBudgetCommand.COMMAND_WORD + " c/others",
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);

        /* Case: missing category -> rejected */
        assertCommandFailure(DeleteBudgetCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (random string without category prefix) -> rejected */
        assertCommandFailure(DeleteBudgetCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + " 1 abc",
                Category.MESSAGE_CONSTRAINTS);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEbudGEt" + CATEGORY_DESC_BUDGET, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Budget} for the specified {@code category} in {@code model}'s finance tracker.
     * @return the removed budget
     */
    private Budget removeBudget(Model model, Category category) {
        Budget targetBudget = getBudget(model, category);
        model.deleteBudget(targetBudget);
        return targetBudget;
    }

    /**
     * Deletes the budget for {@code toDelete} by creating a default {@code DeleteBudgetCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteBudgetCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    /*private void assertCommandSuccess(Category toDelete) {
        Model expectedModel = getModel();
        Budget deletedBudget = removeBudget(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_BUDGET_SUCCESS, deletedBudget);

        assertCommandSuccess(
                DeleteBudgetCommand.COMMAND_WORD + " c/" + toDelete, expectedModel, expectedResultMessage);
    }*/

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
     * and selected card are expected to update accordingly depending on card at {@code expectedSelectedCardCategory}.
     * @see DeleteBudgetCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see FinanceTrackerSystemTest#assertSelectedBudgetCardChanged(Category)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Category expectedSelectedCardCategory) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardCategory != null) {
            assertSelectedBudgetCardChanged(expectedSelectedCardCategory);
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
        //assertSelectedBudgetCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
