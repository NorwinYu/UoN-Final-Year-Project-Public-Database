package seedu.address.logic.commands.debtcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
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
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.debt.Debt;
import seedu.address.testutil.Assert;
import seedu.address.testutil.DebtBuilder;
import seedu.address.testutil.EditDebtDescriptorBuilder;

/**
 * Contains unit tests for EditDebtCommand.
 * Also includes integration tests that has interaction with the Model.
 * Include integration tests with UndoCommand and RedoCommand.
 */
public class EditDebtCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws ParseException {
        Debt editedDebt = new DebtBuilder().build();
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder(editedDebt).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(INDEX_FIRST_DEBT, descriptor);

        String expectedMessage = String.format(EditDebtCommand.MESSAGE_EDIT_DEBT_SUCCESS, editedDebt);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setDebt(model.getFilteredDebtList().get(0), editedDebt);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws ParseException {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredDebtList().size());
        Debt lastDebt = model.getFilteredDebtList().get(indexLastPerson.getZeroBased());

        DebtBuilder debtInList = new DebtBuilder(lastDebt);
        Debt editedDebt = debtInList.withPersonOwed(VALID_NAME_DEBT).withAmount(VALID_AMOUNT_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();

        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder().withPersonOwed(
                VALID_NAME_DEBT).withAmount(VALID_AMOUNT_DEBT).withDeadline(VALID_DEADLINE_DEBT)
                .withRemarks(VALID_REMARKS_DEBT).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditDebtCommand.MESSAGE_EDIT_DEBT_SUCCESS, editedDebt);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setDebt(lastDebt, editedDebt);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws ParseException {
        EditDebtCommand editDebtCommand = new EditDebtCommand(INDEX_FIRST_DEBT,
                new EditDebtCommand.EditDebtDescriptor());
        Debt editedDebt = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());

        String expectedMessage = String.format(EditDebtCommand.MESSAGE_EDIT_DEBT_SUCCESS, editedDebt);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws ParseException {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);

        Debt debtInFilteredList = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        Debt editedDebt = new DebtBuilder(debtInFilteredList).withPersonOwed(VALID_NAME_DEBT).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(INDEX_FIRST_DEBT,
                new EditDebtDescriptorBuilder().withPersonOwed(VALID_NAME_DEBT).build());

        String expectedMessage = String.format(EditDebtCommand.MESSAGE_EDIT_DEBT_SUCCESS, editedDebt);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setDebt(model.getFilteredDebtList().get(0), editedDebt);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editDebtCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDebtIndexUnfilteredList_failure() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDeadline_throwsParseException() {
        String invalidDeadline = "12-12-2018";
        Assert.assertThrows(ParseException.class, () -> new EditDebtDescriptorBuilder()
                .withDeadline(invalidDeadline).build());
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of finance tracker
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws ParseException {
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        Index outOfBoundIndex = INDEX_SECOND_DEBT;
        // ensures that outOfBoundIndex is still in bounds of finance tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinanceTracker().getDebtList().size());

        EditDebtCommand editDebtCommand = new EditDebtCommand(outOfBoundIndex,
                new EditDebtDescriptorBuilder().withPersonOwed(VALID_NAME_DEBT).build());

        assertCommandFailure(editDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Debt editedDebt = new DebtBuilder().build();
        Debt debtToEdit = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder(editedDebt).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(INDEX_FIRST_DEBT, descriptor);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setDebt(debtToEdit, editedDebt);
        expectedModel.commitFinanceTracker();

        // edit -> first debt edited
        editDebtCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered debt list to show all debts
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first debt edited again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDebtList().size() + 1);
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(outOfBoundIndex, descriptor);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(editDebtCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Debt} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited debt in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the debt object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Debt editedDebt = new DebtBuilder().build();
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder(editedDebt).build();
        EditDebtCommand editDebtCommand = new EditDebtCommand(INDEX_FIRST_DEBT, descriptor);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());

        showDebtAtIndex(model, INDEX_SECOND_DEBT);
        Debt debtToEdit = model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased());
        expectedModel.setDebt(debtToEdit, editedDebt);
        expectedModel.commitFinanceTracker();

        // edit -> edits second debt in unfiltered debt list / first debt in filtered debt list
        editDebtCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered debt list to show all debts
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredDebtList().get(INDEX_FIRST_DEBT.getZeroBased()), debtToEdit);
        // redo -> edits same second debt in unfiltered debt list
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws ParseException {
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).withAmount(VALID_AMOUNT_DEBT)
                .withCategory(VALID_CATEGORY_DEBT).withDeadline(VALID_DEADLINE_DEBT)
                .withRemarks(VALID_REMARKS_DEBT).build();
        final EditDebtCommand standardCommand = new EditDebtCommand(INDEX_FIRST_DEBT, descriptor);

        // same values -> returns true
        EditDebtCommand.EditDebtDescriptor copyDescriptor =
                new EditDebtCommand.EditDebtDescriptor(descriptor);
        EditDebtCommand commandWithSameValues = new EditDebtCommand(INDEX_FIRST_DEBT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(5));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDebtCommand(INDEX_SECOND_DEBT, descriptor)));

        // different descriptor -> returns false
        EditDebtCommand.EditDebtDescriptor differentDescriptor = new EditDebtCommand.EditDebtDescriptor();
        assertFalse(standardCommand.equals(new EditDebtCommand(INDEX_FIRST_DEBT, differentDescriptor)));
    }

}
