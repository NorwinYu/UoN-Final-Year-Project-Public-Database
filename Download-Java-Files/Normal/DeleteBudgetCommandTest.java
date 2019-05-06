package seedu.address.logic.commands.budgetcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.attributes.Category.FOOD;
import static seedu.address.model.attributes.Category.OTHERS;
import static seedu.address.model.attributes.Category.UTILITIES;
import static seedu.address.model.attributes.Category.WORK;
import static seedu.address.testutil.TypicalBudgets.getTypicalFinanceTrackerWithBudgets;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBudgetCommand}.
 */
public class DeleteBudgetCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCategoryFilteredList_success() {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == FOOD) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget budgetToDelete = model.getFilteredBudgetList().get(index);
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(FOOD);

        String expectedMessage = String.format(DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(deleteBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noBudgetforThatCategoryFilteredList_throwsCommandException() {

        Category category = OTHERS;
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
                break;
            }
        }
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(category);

        assertCommandFailure(deleteBudgetCommand, model, commandHistory,
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);
    }

    @Test
    public void executeUndoRedo_validCategoryUnfilteredList_success() throws Exception {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == FOOD) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget budgetToDelete = model.getFilteredBudgetList().get(index);
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(FOOD);

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.deleteBudget(budgetToDelete);
        expectedModel.commitFinanceTracker();

        // delete -> food budget deleted
        deleteBudgetCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered expense list to show all budgets
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same budget (food) deleted again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidCategoryUnfilteredList_failure() {
        DeleteBudgetCommand deleteBudgetCommand = new DeleteBudgetCommand(OTHERS);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(deleteBudgetCommand, model, commandHistory,
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteBudgetCommand deleteUtilitiesCommand = new DeleteBudgetCommand(UTILITIES);
        DeleteBudgetCommand deleteWorkCommand = new DeleteBudgetCommand(WORK);
        DeleteBudgetCommand deleteWorkCommandCopy = new DeleteBudgetCommand(WORK);

        //same values -> returns true
        assertTrue(deleteWorkCommand.equals(deleteWorkCommandCopy));

        // same object -> returns true
        assertTrue(deleteWorkCommand.equals(deleteWorkCommand));

        // different types -> returns false
        assertFalse(deleteWorkCommand.equals(1));

        // null -> returns false
        assertFalse(deleteWorkCommand.equals(null));

        // different expense -> returns false
        assertFalse(deleteWorkCommand.equals(deleteUtilitiesCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show nothing.
     */
    private void showNothing(Model model) {
        model.updateFilteredBudgetList(p -> false);

        assertTrue(model.getFilteredBudgetList().isEmpty());
    }
}
