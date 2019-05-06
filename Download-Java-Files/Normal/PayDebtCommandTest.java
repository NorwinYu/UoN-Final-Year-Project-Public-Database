package seedu.address.logic.commands.debtcommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE_2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDebtAtIndex;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEBT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.Date;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains unit tests for PayDebtCommand.
 * Also includes integration tests that has interaction with the Model.
 * Include integration tests with UndoCommand and RedoCommand.
 */
public class PayDebtCommandTest {

    private Model model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private final Date validDate = new Date(VALID_DATE_EXPENSE);
    private final Date validDate2 = new Date(VALID_DATE_EXPENSE_2);

    @Test
    public void execute_validFieldsUnfilteredList_success() {
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        PayDebtCommand payDebtCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        Expense expenseToAdd = new ExpenseBuilder()
                .withName("Paid Debt to " + debtToDelete.getPersonOwed())
                .withAmount(debtToDelete.getAmount().toString())
                .withCategory(debtToDelete.getCategory().toString())
                .withDate(VALID_DATE_EXPENSE).withRemarks(debtToDelete.getRemarks()).build();

        String expectedMessage = String.format(PayDebtCommand.MESSAGE_CONVERT_DEBT_SUCCESS,
                debtToDelete, expenseToAdd);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.addExpense(expenseToAdd);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(payDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        PayDebtCommand payDebtCommand = new PayDebtCommand(outOfBoundIndex, validDate);

        assertCommandFailure(payDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validFieldsFilteredList_success() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);

        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        PayDebtCommand payDebtCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        Expense expenseToAdd = new ExpenseBuilder()
                .withName("Paid Debt to " + debtToDelete.getPersonOwed())
                .withAmount(debtToDelete.getAmount().toString())
                .withCategory(debtToDelete.getCategory().toString())
                .withDate(VALID_DATE_EXPENSE).withRemarks(debtToDelete.getRemarks()).build();

        String expectedMessage = String.format(PayDebtCommand.MESSAGE_CONVERT_DEBT_SUCCESS,
                debtToDelete, expenseToAdd);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.addExpense(expenseToAdd);
        expectedModel.commitFinanceTracker();
        showNoDebt(expectedModel);

        assertCommandSuccess(payDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);

        Index outOfBoundIndex = INDEX_SECOND_DEBT;
        // ensures that outOfBoundIndex is still in bounds of finance tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getDebtList().size());

        PayDebtCommand payDebtCommand = new PayDebtCommand(outOfBoundIndex, validDate);

        assertCommandFailure(payDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws CommandException {
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        Expense expenseToAdd = new ExpenseBuilder()
                .withName("Paid Debt to " + debtToDelete.getPersonOwed())
                .withAmount(debtToDelete.getAmount().toString())
                .withCategory(debtToDelete.getCategory().toString())
                .withDate(VALID_DATE_EXPENSE).withRemarks(debtToDelete.getRemarks()).build();

        PayDebtCommand payDebtCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.addExpense(expenseToAdd);
        expectedModel.commitFinanceTracker();

        // pay debt -> first debt deleted and expense added accordingly
        payDebtCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered debt list to show all persons
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first debt deleted and same expense added again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        PayDebtCommand payDebtCommand = new PayDebtCommand(outOfBoundIndex, validDate);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(payDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Converts a {@code Debt} from a filtered list to {@code Expense}.
     * 2. Undo the conversion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted debt in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the debt object regardless of indexing.
     * 5. Verify that the expense created from the conversion is the same as the expense in the unfiltered
     * expense list.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameDebtDeletedAndExpenseAdded() throws CommandException {
        PayDebtCommand payDebtCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());

        showDebtAtIndex(model, INDEX_SECOND_DEBT);
        Debt debtToDelete = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        Expense expenseToAdd = new ExpenseBuilder()
                .withName("Paid Debt to " + debtToDelete.getPersonOwed())
                .withAmount(debtToDelete.getAmount().toString())
                .withCategory(debtToDelete.getCategory().toString())
                .withDate(VALID_DATE_EXPENSE).withRemarks(debtToDelete.getRemarks()).build();
        expectedModel.deleteDebt(debtToDelete);
        expectedModel.addExpense(expenseToAdd);
        expectedModel.commitFinanceTracker();

        // deletes second debt in unfiltered debt list / first debt in filtered debt list
        // adds expense accordingly
        payDebtCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered debt list to show all debts
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(debtToDelete, model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased()));
        // redo -> deletes same second debt in unfiltered debt list. adds same expense back.
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(expenseToAdd, model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased()));
    }

    @Test
    public void equals() {
        PayDebtCommand payDebtFirstCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        PayDebtCommand payDebtSecondCommand = new PayDebtCommand(INDEX_SECOND_DEBT, validDate);
        PayDebtCommand payDebtThirdCommand = new PayDebtCommand(INDEX_FIRST_DEBT, validDate2);

        // same object -> returns true
        assertTrue(payDebtFirstCommand.equals(payDebtFirstCommand));

        // same values -> returns true
        PayDebtCommand payDebtFirstCommandCopy = new PayDebtCommand(INDEX_FIRST_DEBT, validDate);
        assertTrue(payDebtFirstCommand.equals(payDebtFirstCommandCopy));

        // different types -> returns false
        assertFalse(payDebtFirstCommand.equals(1));

        // null -> returns false
        assertFalse(payDebtFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(payDebtFirstCommand.equals(payDebtSecondCommand));

        // different date -> returns false
        assertFalse(payDebtFirstCommand.equals(payDebtThirdCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDebt(Model model) {
        model.updateFilteredDebtList(p -> false);

        assertTrue(model.getFilteredDebtList().isEmpty());
    }
}
