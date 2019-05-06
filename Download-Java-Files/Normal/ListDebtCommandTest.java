package seedu.address.logic.commands.debtcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDebtAtIndex;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDebtCommand.
 */
public class ListDebtCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    // Tests that involves listing ALL
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDebtCommand(View.ALL), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showDebtAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListDebtCommand(View.ALL), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    // Tests that involves Category
    @Test
    public void execute_listIsNotFiltered_showsDebtsWithFoodCategory() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_FOOD_DEBTS);
        assertCommandSuccess(new ListDebtCommand(View.FOOD), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.FOOD.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDebtsWithFoodCategory() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_FOOD_DEBTS);
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        assertCommandSuccess(new ListDebtCommand(View.FOOD), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.FOOD.getMessage()), expectedModel);
    }

    // Tests that involves Date
    @Test
    public void execute_listIsNotFiltered_showsDebtsDueInOneWeek() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_WEEK_DEBTS);
        assertCommandSuccess(new ListDebtCommand(View.WEEK), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.WEEK.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDebtsDueInOneWeek() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_WEEK_DEBTS);
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        assertCommandSuccess(new ListDebtCommand(View.WEEK), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.WEEK.getMessage()), expectedModel);
    }

    // Tests that involves Amount
    @Test
    public void execute_listIsNotFiltered_showsDebtsWithAmountOver1000() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_AMOUNT_OVER_1000_DEBTS);
        assertCommandSuccess(new ListDebtCommand(View.$1000), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.$1000.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDebtsWithAmountOver1000() {
        expectedModel.updateFilteredDebtList(Model.PREDICATE_SHOW_AMOUNT_OVER_1000_DEBTS);
        showDebtAtIndex(model, INDEX_FIRST_DEBT);
        assertCommandSuccess(new ListDebtCommand(View.$1000), model, commandHistory,
                String.format(ListDebtCommand.MESSAGE_SUCCESS, View.$1000.getMessage()), expectedModel);
    }

}
