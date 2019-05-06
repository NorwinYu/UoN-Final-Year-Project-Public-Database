package seedu.address.logic.commands.debtcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearDebtCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyFinanceTracker_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearDebtCommand(), model, commandHistory, ClearDebtCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyFinanceTracker_success() {
        Model model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearDebtCommand(), model, commandHistory, ClearDebtCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
