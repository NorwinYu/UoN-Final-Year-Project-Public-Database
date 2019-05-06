package seedu.address.logic.commands.recurringcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.RecurringBuilder;

/**
 * Contains unit tests for ConvertRecurringToExpenseCommand.
 *
 * Also includes integration tests that has interaction with the Model.
 * Include integration tests with UndoCommand and RedoCommand.
 */
public class ConvertRecurringToExpenseCommandTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRecurringUnfilteredList_success() {
        ConvertRecurringToExpenseCommand convertRecurringToExpenseCommand = new ConvertRecurringToExpenseCommand();

        String expectedMessage = ConvertRecurringToExpenseCommand.MESSAGE_CONVERT_RECURRING_SUCCESS + "0.";

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(convertRecurringToExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneValidRecurringUnfilteredList_success() {
        Recurring recurringToAdd = new RecurringBuilder().withName("2017").withAmount("1")
                .withDate("01-01-2017").withCategory("food").withFrequency("Y").withOccurrence("24").build();
        model.addRecurring(recurringToAdd);

        ConvertRecurringToExpenseCommand convertRecurringToExpenseCommand = new ConvertRecurringToExpenseCommand();

        String expectedMessage = ConvertRecurringToExpenseCommand.MESSAGE_CONVERT_RECURRING_SUCCESS + "3.";

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        for (int i = 0; i < 3; i++) {
            expectedModel.addExpense(recurringToAdd.getRecurringListOfExpenses().get(i));
        }
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(convertRecurringToExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addAnotherValidRecurringUnfilteredList_success() {
        Recurring anotherRecurringToAdd = new RecurringBuilder().withName("2019").withAmount("1")
                .withDate("01-01-2019").withCategory("food").withFrequency("Y").withOccurrence("24").build();
        model.addRecurring(anotherRecurringToAdd);

        ConvertRecurringToExpenseCommand convertRecurringToExpenseCommand = new ConvertRecurringToExpenseCommand();

        String expectedMessage = ConvertRecurringToExpenseCommand.MESSAGE_CONVERT_RECURRING_SUCCESS + "1.";

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addExpense(anotherRecurringToAdd.getRecurringListOfExpenses().get(0));
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(convertRecurringToExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidRecurringUnfilteredList_success() {
        model = new ModelManager();
        commandHistory = new CommandHistory();

        Recurring recurringToAdd = new RecurringBuilder().withName("2017").withAmount("1")
                .withDate("01-01-2017").withCategory("food").withFrequency("Y").withOccurrence("24").build();
        model.addRecurring(recurringToAdd);
        Recurring anotherRecurringToAdd = new RecurringBuilder().withName("2019").withAmount("1")
                .withDate("01-01-2019").withCategory("food").withFrequency("Y").withOccurrence("24").build();
        model.addRecurring(anotherRecurringToAdd);

        ConvertRecurringToExpenseCommand convertRecurringToExpenseCommand = new ConvertRecurringToExpenseCommand();

        String expectedMessage = ConvertRecurringToExpenseCommand.MESSAGE_CONVERT_RECURRING_SUCCESS + "4.";

        ModelManager expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        for (int i = 0; i < 3; i++) {
            expectedModel.addExpense(recurringToAdd.getRecurringListOfExpenses().get(i));
        }
        expectedModel.addExpense(anotherRecurringToAdd.getRecurringListOfExpenses().get(0));
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(convertRecurringToExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
