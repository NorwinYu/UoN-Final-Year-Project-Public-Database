package seedu.address.logic.commands.expensecommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearExpenseCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory, ClearExpenseCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory, ClearExpenseCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
