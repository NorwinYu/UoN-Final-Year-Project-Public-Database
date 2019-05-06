package seedu.address.logic.commands.recurringcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecurringAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalRecurrings.getTypicalFinanceTrackerWithRecurrings;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListRecurringCommand.
 */
public class ListRecurringCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithRecurrings(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    // Tests that involves listing ALL
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListRecurringCommand(View.ALL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.ALL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    // Tests that involves Category
    @Test
    public void execute_listIsNotFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_TRAVEL_RECURRING);
        assertCommandSuccess(new ListRecurringCommand(View.TRAVEL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.TRAVEL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsHealthCareCategory() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_TRAVEL_RECURRING);
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.TRAVEL), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.TRAVEL.getMessage()), expectedModel);
    }

    // Tests that involves Date
    @Test
    public void execute_listIsNotFiltered_showsRecurringWithinLastYear() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_YEAR_RECURRING);
        assertCommandSuccess(new ListRecurringCommand(View.YEAR), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.YEAR.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsRecurringWithinLastYear() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_YEAR_RECURRING);
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.YEAR), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.YEAR.getMessage()), expectedModel);
    }

    // Tests that involves Amount
    @Test
    public void execute_listIsNotFiltered_showsRecurringWithAmountOver10() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_AMOUNT_OVER_10_RECURRING);
        assertCommandSuccess(new ListRecurringCommand(View.$10), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.$10.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsRecurringWithAmountOver10() {
        expectedModel.updateFilteredRecurringList(Model.PREDICATE_SHOW_AMOUNT_OVER_10_RECURRING);
        showRecurringAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListRecurringCommand(View.$10), model, commandHistory,
                String.format(ListRecurringCommand.MESSAGE_SUCCESS, View.$10.getMessage()), expectedModel);
    }
}
