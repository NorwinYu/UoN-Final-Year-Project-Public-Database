package seedu.address.logic.commands.budgetcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBudgets.getTypicalFinanceTrackerWithBudgets;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearBudgetCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearBudgetCommand(), model, commandHistory, ClearBudgetCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithBudgets(), new UserPrefs());
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearBudgetCommand(), model, commandHistory, ClearBudgetCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
