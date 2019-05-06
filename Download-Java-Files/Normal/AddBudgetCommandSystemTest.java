package systemtests.budgetsystemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.testutil.TypicalBudgets.BUDGET;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;

import org.junit.Test;

import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;
import seedu.address.testutil.BudgetUtil;
import systemtests.FinanceTrackerSystemTest;

public class AddBudgetCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a budget to a non-empty finance tracker, command with leading spaces and trailing spaces
         * -> added
         */
        Budget toAdd = BUDGET;
        toAdd.setCategory(Category.OTHERS);
        String command = "   " + AddBudgetCommand.COMMAND_WORD + "  c/others  " + AMOUNT_DESC_BUDGET
                + " " + STARTDATE_DESC_BUDGET + "   " + ENDDATE_DESC_BUDGET + "   " + REMARKS_DESC_BUDGET + "   ";
        assertCommandSuccess(command, toAdd);


        /* Case: undo adding budget to the list -> budget deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding budget to the list -> budget added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBudget(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add to empty finance tracker -> added */
        deleteAllBudgets();
        toAdd.setCategory(Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase()));
        command = "   " + AddBudgetCommand.COMMAND_WORD + "  " + CATEGORY_DESC_BUDGET + " " + AMOUNT_DESC_BUDGET + " "
                + STARTDATE_DESC_BUDGET + "   " + ENDDATE_DESC_BUDGET + " " + REMARKS_DESC_BUDGET + " ";
        assertCommandSuccess(command, toAdd);

        /* ------------------------ Perform add operation while a budget card is selected -------------------------- */
        /* Case: selects food budget card in the budget list, add a budget -> added, card selection remains unchanged */
        selectBudget(Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase()));
        assertCommandSuccess(FOOD_BUDGET);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: missing category -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET
                + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));

        /* Case: missing amount -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET
                + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));

        /* Case: missing end date -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addsbudget " + BudgetUtil.getBudgetDetails(toAdd);
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid category -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + INVALID_CATEGORY_DESC + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Category.MESSAGE_CONSTRAINTS);

        /* Case: invalid amount -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + INVALID_AMOUNT_DESC + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Amount.MESSAGE_CONSTRAINTS);

        /* Case: invalid start date format -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                + INVALID_STARTDATE_DESC_FORMAT + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                + INVALID_STARTDATE_DESC_EXIST + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Date.MESSAGE_DATE_DOES_NOT_EXIST);

        /* Case: invalid end date format -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                + STARTDATE_DESC_BUDGET + INVALID_ENDDATE_DESC_FORMAT + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);

        /* Case: invalid end date -> rejected */
        command = AddBudgetCommand.COMMAND_WORD + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                + STARTDATE_DESC_BUDGET + INVALID_ENDDATE_DESC_EXIST + REMARKS_DESC_BUDGET;
        assertCommandFailure(command, Date.MESSAGE_DATE_DOES_NOT_EXIST);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("adDBuDGet" + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes the {@code AddBudgetCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddBudgetCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code BudgetListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Budget toAdd) {
        assertCommandSuccess(BudgetUtil.getAddBudgetCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Expense)}. Executes {@code command}
     * instead.
     *
     * @see AddBudgetCommandSystemTest#assertCommandSuccess(Budget)
     */
    private void assertCommandSuccess(String command, Budget toAdd) {
        Model expectedModel = getModel();
        expectedModel.addBudget(toAdd);
        String expectedResultMessage = String.format(AddBudgetCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Expense)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ExpenseListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddBudgetCommandSystemTest#assertCommandSuccess(String, Budget)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ExpenseListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
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
