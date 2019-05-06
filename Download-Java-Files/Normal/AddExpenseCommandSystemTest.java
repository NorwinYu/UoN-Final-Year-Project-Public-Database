package systemtests.expensesystemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.EXPENSE;
import static seedu.address.testutil.TypicalExpenses.GROCERIES;
import static seedu.address.testutil.TypicalExpenses.KEYWORD_MATCHING_CHICKEN;
import static seedu.address.testutil.TypicalExpenses.STOCKS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.AddExpenseCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseUtil;
import systemtests.FinanceTrackerSystemTest;

public class AddExpenseCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an expense to a non-empty finance tracker, command with leading spaces and trailing spaces
         * -> added
         */
        Expense toAdd = EXPENSE;
        String command = "   " + AddExpenseCommand.COMMAND_WORD + "  " + NAME_DESC_EXPENSE + "  " + AMOUNT_DESC_EXPENSE
                + " " + CATEGORY_DESC_EXPENSE + "   " + DATE_DESC_EXPENSE + "   " + REMARKS_DESC_EXPENSE + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Expense to the list -> Expense deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Expense to the list -> Expense added again */
        command = RedoCommand.COMMAND_WORD;
        model.addExpense(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add to empty finance tracker -> added */
        deleteAllExpenses();
        command = "   " + AddExpenseCommand.COMMAND_WORD + "  " + NAME_DESC_EXPENSE + "  " + AMOUNT_DESC_EXPENSE + " "
                + CATEGORY_DESC_EXPENSE + "   " + DATE_DESC_EXPENSE + "   " + REMARKS_DESC_EXPENSE + " ";
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the expense list before adding -> added */
        showExpensesWithName(KEYWORD_MATCHING_CHICKEN);
        assertCommandSuccess(STOCKS);

        /* ------------------------ Perform add operation while a expense card is selected -------------------------- */

        /* Case: selects first card in the expense list, add a expense -> added, card selection remains unchanged */
        selectExpense(Index.fromOneBased(1));
        assertCommandSuccess(GROCERIES);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case:
        missing name -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE
                + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));

        /* Case: missing amount -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + NAME_DESC_EXPENSE + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE
                + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));

        /* Case: missing category -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + DATE_DESC_EXPENSE
                + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ExpenseUtil.getExpenseDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + INVALID_NAME_DESC + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid amount -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + NAME_DESC_EXPENSE + INVALID_AMOUNT_DESC + CATEGORY_DESC_EXPENSE
                + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, Amount.MESSAGE_CONSTRAINTS);

        /* Case: invalid category -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + INVALID_CATEGORY_DESC
                + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, Category.MESSAGE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddExpenseCommand.COMMAND_WORD + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + INVALID_DATE_DESC_FORMAT + REMARKS_DESC_EXPENSE;
        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);

    }

    /**
     * Executes the {@code AddExpenseCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddExpenseCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ExpenseListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Expense toAdd) {
        assertCommandSuccess(ExpenseUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Expense)}. Executes {@code command}
     * instead.
     * @see AddExpenseCommandSystemTest#assertCommandSuccess(Expense)
     */
    private void assertCommandSuccess(String command, Expense toAdd) {
        Model expectedModel = getModel();
        expectedModel.addExpense(toAdd);
        String expectedResultMessage = String.format(AddExpenseCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Expense)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ExpenseListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddExpenseCommandSystemTest#assertCommandSuccess(String, Expense)
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
