package seedu.address.logic.commands.recurringcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecurrings.getTypicalFinanceTrackerWithRecurrings;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearRecurringCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearRecurringCommand(), model, commandHistory, ClearRecurringCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
        expectedModel.setFinanceTracker(new FinanceTracker());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ClearRecurringCommand(), model, commandHistory, ClearRecurringCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
