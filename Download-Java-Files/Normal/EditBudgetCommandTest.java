package seedu.address.logic.commands.budgetcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.attributes.Category.FOOD;
import static seedu.address.model.attributes.Category.OTHERS;
import static seedu.address.model.attributes.Category.TRAVEL;
import static seedu.address.model.attributes.Category.WORK;
import static seedu.address.testutil.TypicalBudgets.getTypicalFinanceTrackerWithBudgets;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;
import seedu.address.testutil.BudgetBuilder;
import seedu.address.testutil.EditBudgetDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditBudgetCommand.
 */
public class EditBudgetCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Category category = WORK;
        Budget editedBudget = new BudgetBuilder().withCategory(category.toString()).build();
        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder(editedBudget).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category, descriptor);

        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
                break;
            }
        }

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setBudget(model.getFilteredBudgetList().get(index), editedBudget);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Category category = FOOD;


        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget foodBudget = model.getFilteredBudgetList().get(index);
        BudgetBuilder budgetInList = new BudgetBuilder(foodBudget);

        Budget editedBudget = budgetInList.withAmount(VALID_AMOUNT_DEBT)
                .withEndDate(VALID_ENDDATE_BUDGET).build();

        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder(editedBudget).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category, descriptor);

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setBudget(foodBudget, editedBudget);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Category category = FOOD;
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }

        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category,
                new EditBudgetCommand.EditBudgetDescriptor());
        Budget editedBudget = model.getFilteredBudgetList().get(index);

        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget);

        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCategoryUnfilteredList_failure() {
        Category category = OTHERS;

        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_AMOUNT_BUDGET).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category, descriptor);

        assertCommandFailure(editBudgetCommand, model, commandHistory,
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);
    }

    @Test
    public void executeUndoRedo_validCategoryUnfilteredList_success() throws Exception {
        Category category = FOOD;
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        Budget editedBudget = new BudgetBuilder().build();
        Budget budgetToEdit = model.getFilteredBudgetList().get(index);
        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder(editedBudget).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category, descriptor);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.setBudget(budgetToEdit, editedBudget);
        expectedModel.commitFinanceTracker();

        // edit -> food expense edited
        editBudgetCommand.execute(model, commandHistory);

        // undo -> reverts finance tracker back to previous state and filtered budget list to show all budgets
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same budget (food) edited again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidICategoryUnfilteredList_failure() {
        Category category = OTHERS;
        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_AMOUNT_BUDGET).build();
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(category, descriptor);

        // execution failed -> finance tracker state not added into model
        assertCommandFailure(editBudgetCommand, model, commandHistory,
                Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);

        // single finance tracker state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditBudgetCommand standardCommand = new EditBudgetCommand(FOOD, DESC_BUDGET);

        // same values -> returns true
        EditBudgetCommand.EditBudgetDescriptor copyDescriptor = new EditBudgetCommand.EditBudgetDescriptor(DESC_BUDGET);
        EditBudgetCommand commandWithSameValues = new EditBudgetCommand(FOOD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new DeleteBudgetCommand(FOOD)));

        // different category -> returns false
        assertFalse(standardCommand.equals(new EditBudgetCommand(TRAVEL, DESC_BUDGET)));

        // different descriptor -> returns false
        EditBudgetCommand.EditBudgetDescriptor differentDescriptor = new EditBudgetCommand.EditBudgetDescriptor();
        assertFalse(standardCommand.equals(new EditBudgetCommand(FOOD, differentDescriptor)));
    }
}
