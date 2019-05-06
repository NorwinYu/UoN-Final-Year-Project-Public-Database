package seedu.address.logic.commands.expensecommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;
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
 * Contains integration tests (interaction with the Model) and unit tests for ListExpenseCommand.
 */
public class ListExpenseCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    // Tests that involves listing ALL
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListExpenseCommand(View.ALL), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListExpenseCommand(View.ALL), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.ALL.getMessage()), expectedModel);
    }

    // Tests that involves Category
    @Test
    public void execute_listIsNotFiltered_showsExpensesWithHealthCareCategory() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_HEALTHCARE_EXPENSES);
        assertCommandSuccess(new ListExpenseCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.HEALTHCARE.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsExpensesWithHealthCareCategory() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_HEALTHCARE_EXPENSES);
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListExpenseCommand(View.HEALTHCARE), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.HEALTHCARE.getMessage()), expectedModel);
    }

    // Tests that involves Date
    @Test
    public void execute_listIsNotFiltered_showsExpensesWithinLastMonth() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_MONTH_EXPENSES);
        assertCommandSuccess(new ListExpenseCommand(View.MONTH), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.MONTH.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsExpensesWithinLastMonth() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_MONTH_EXPENSES);
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListExpenseCommand(View.MONTH), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.MONTH.getMessage()), expectedModel);
    }

    // Tests that involves Amount
    @Test
    public void execute_listIsNotFiltered_showsExpensesWithAmountOver100() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_AMOUNT_OVER_100_EXPENSES);
        assertCommandSuccess(new ListExpenseCommand(View.$100), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.$100.getMessage()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsExpensesWithAmountOver100() {
        expectedModel.updateFilteredExpenseList(Model.PREDICATE_SHOW_AMOUNT_OVER_100_EXPENSES);
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListExpenseCommand(View.$100), model, commandHistory,
                String.format(ListExpenseCommand.MESSAGE_SUCCESS, View.$100.getMessage()), expectedModel);
    }
}
