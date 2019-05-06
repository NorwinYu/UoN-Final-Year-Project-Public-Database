package seedu.address.logic.commands.recurringcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRecurrings.getTypicalFinanceTrackerWithRecurrings;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.RecurringBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddRecurringCommand}.
 */
public class AddRecurringCommandIntegrationTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Recurring validRecurring = new RecurringBuilder().build();

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addRecurring(validRecurring);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new AddRecurringCommand(validRecurring), model, commandHistory,
                String.format(AddRecurringCommand.MESSAGE_SUCCESS, validRecurring), expectedModel);
    }
}
