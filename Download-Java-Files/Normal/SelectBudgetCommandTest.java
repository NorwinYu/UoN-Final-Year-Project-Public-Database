package seedu.address.logic.commands.budgetcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBudgets.getTypicalFinanceTrackerWithBudgets;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectBudgetCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCategory_success() {
        Category foodCategory = Category.FOOD;
        Category workCategory = Category.WORK;

        assertExecutionSuccess(foodCategory);
        assertExecutionSuccess(workCategory);
    }

    @Test
    public void execute_invalidCategory_failure() {
        Category othersCategory = Category.OTHERS;

        assertExecutionFailure(othersCategory, Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);
    }

    @Test
    public void equals() {
        SelectBudgetCommand selectWorkCommand = new SelectBudgetCommand(Category.WORK);
        SelectBudgetCommand selectFoodCommand = new SelectBudgetCommand(Category.FOOD);

        // same object -> returns true
        assertTrue(selectFoodCommand.equals(selectFoodCommand));

        // same values -> returns true
        SelectBudgetCommand selectFoodCommandCopy = new SelectBudgetCommand(Category.FOOD);
        assertTrue(selectFoodCommand.equals(selectFoodCommandCopy));

        // different types -> returns false
        assertFalse(selectFoodCommand.equals(1));

        // null -> returns false
        assertFalse(selectFoodCommand.equals(null));

        // different budget -> returns false
        assertFalse(selectFoodCommand.equals(selectWorkCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected expense is set to the expense at {@code index} in the filtered expense list.
     */
    private void assertExecutionSuccess(Category category) {
        SelectBudgetCommand selectBudgetCommand = new SelectBudgetCommand(category);
        String expectedMessage = String.format(SelectBudgetCommand.MESSAGE_SELECT_BUDGET_SUCCESS, category);
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        expectedModel.setSelectedBudget(model.getFilteredBudgetList().get(index));

        assertCommandSuccess(selectBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Category category, String expectedMessage) {
        SelectBudgetCommand selectBudgetCommand = new SelectBudgetCommand(category);
        assertCommandFailure(selectBudgetCommand, model, commandHistory, expectedMessage);
    }
}
