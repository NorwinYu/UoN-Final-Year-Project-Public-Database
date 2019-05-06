package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expensecommands.EditExpenseCommand;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.debt.Debt;
import seedu.address.model.debt.NameContainsKeywordsPredicateForDebt;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.NameContainsKeywordsPredicateForExpense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.recurring.RecurringNameContainsKeywordsPredicate;
import seedu.address.testutil.EditBudgetDescriptorBuilder;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.EditRecurringDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_EXPENSE = "Chicken Rice";
    public static final String VALID_NAME_DEBT = "Bob Choo";
    public static final String VALID_NAME_DEBT_2 = "Jordan Foo";
    public static final String VALID_NAME_RECURRING = "Phone Bill";
    public static final String VALID_AMOUNT_EXPENSE = "4";
    public static final String VALID_AMOUNT_DEBT = "12345";
    public static final String VALID_AMOUNT_DEBT_2 = "123.45";
    public static final String VALID_AMOUNT_BUDGET = "55555";
    public static final String VALID_CATEGORY_EXPENSE = "food";
    public static final String VALID_CATEGORY_EXPENSE_2 = "transport";
    public static final String VALID_CATEGORY_DEBT = "shopping";
    public static final String VALID_CATEGORY_DEBT_2 = "TRANSPORT";
    public static final String VALID_CATEGORY_BUDGET = "travel";
    public static final String VALID_DATE_EXPENSE = "03-03-2019";
    public static final String VALID_DATE_EXPENSE_2 = "12-03-2019";
    public static final String VALID_STARTDATE_BUDGET = "01-12-2019";
    public static final String VALID_STARTDATE_BUDGET_2 = "01-06-2019";
    public static final String VALID_ENDDATE_BUDGET = "31-12-2019";
    public static final String VALID_ENDDATE_BUDGET_2 = "30-06-2019";
    public static final String VALID_DEADLINE_DEBT = "05-05-2020";
    public static final String VALID_DEADLINE_DEBT_2 = "01-01-2024";
    public static final String VALID_REMARKS_EXPENSE = "Bishan chicken rice";
    public static final String VALID_REMARKS_DEBT = "fan";
    public static final String VALID_REMARKS_DEBT_2 = "chicken";
    public static final String VALID_REMARKS_BUDGET = "eat more for christmas yay";
    public static final String VALID_AMOUNT_RECURRING = "50.00";
    public static final String VALID_CATEGORY_RECURRING = "utilities";
    public static final String VALID_DATE_RECURRING = "23-02-2019";
    public static final String VALID_REMARKS_RECURRING = "Signed a new 2 year plan.";
    public static final String VALID_FREQUENCY_RECURRING = "M";
    public static final String VALID_OCCURRENCE_RECURRING = "24";
    public static final String VALID_NAME_RECURRING_2 = "EPL Subscription";
    public static final String VALID_AMOUNT_RECURRING_2 = "40";
    public static final String VALID_CATEGORY_RECURRING_2 = "utilities";
    public static final String VALID_DATE_RECURRING_2 = "01-01-2019";
    public static final String VALID_FREQUENCY_RECURRING_2 = "M";
    public static final String VALID_OCCURRENCE_RECURRING_2 = "12";
    public static final String VALID_REMARKS_RECURRING_2 = "Football";

    public static final String NAME_DESC_EXPENSE = " " + PREFIX_NAME + VALID_NAME_EXPENSE;
    public static final String NAME_DESC_DEBT = " " + PREFIX_NAME + VALID_NAME_DEBT;
    public static final String NAME_DESC_DEBT_2 = " " + PREFIX_NAME + VALID_NAME_DEBT_2;
    public static final String AMOUNT_DESC_EXPENSE = " " + PREFIX_AMOUNT + VALID_AMOUNT_EXPENSE;
    public static final String AMOUNT_DESC_DEBT = " " + PREFIX_AMOUNT + VALID_AMOUNT_DEBT;
    public static final String AMOUNT_DESC_DEBT_2 = " " + PREFIX_AMOUNT + VALID_AMOUNT_DEBT_2;
    public static final String AMOUNT_DESC_BUDGET = " " + PREFIX_AMOUNT + VALID_AMOUNT_BUDGET;
    public static final String CATEGORY_DESC_EXPENSE = " " + PREFIX_CATEGORY + VALID_CATEGORY_EXPENSE;
    public static final String CATEGORY_DESC_DEBT = " " + PREFIX_CATEGORY + VALID_CATEGORY_DEBT;
    public static final String CATEGORY_DESC_DEBT_2 = " " + PREFIX_CATEGORY + VALID_CATEGORY_DEBT_2;
    public static final String CATEGORY_DESC_BUDGET = " " + PREFIX_CATEGORY + VALID_CATEGORY_BUDGET;
    public static final String DATE_DESC_EXPENSE = " " + PREFIX_DATE + VALID_DATE_EXPENSE;
    public static final String DATE_DESC_EXPENSE_2 = " " + PREFIX_DATE + VALID_DATE_EXPENSE_2;
    public static final String DEADLINE_DESC_DEBT = " " + PREFIX_DUE + VALID_DEADLINE_DEBT;
    public static final String DEADLINE_DESC_DEBT_2 = " " + PREFIX_DUE + VALID_DEADLINE_DEBT_2;
    public static final String STARTDATE_DESC_BUDGET = " " + PREFIX_STARTDATE + VALID_STARTDATE_BUDGET;
    public static final String STARTDATE_DESC_BUDGET_2 = " " + PREFIX_STARTDATE + VALID_STARTDATE_BUDGET_2;
    public static final String ENDDATE_DESC_BUDGET = " " + PREFIX_ENDDATE + VALID_ENDDATE_BUDGET;
    public static final String ENDDATE_DESC_BUDGET_2 = " " + PREFIX_ENDDATE + VALID_ENDDATE_BUDGET_2;
    public static final String REMARKS_DESC_EXPENSE = " " + PREFIX_REMARKS + VALID_REMARKS_EXPENSE;
    public static final String REMARKS_DESC_DEBT = " " + PREFIX_REMARKS + VALID_REMARKS_DEBT;
    public static final String REMARKS_DESC_DEBT_2 = " " + PREFIX_REMARKS + VALID_REMARKS_DEBT_2;
    public static final String REMARKS_DESC_BUDGET = " " + PREFIX_REMARKS + VALID_REMARKS_BUDGET;

    public static final String NAME_DESC_RECURRING = " " + PREFIX_NAME + VALID_NAME_RECURRING_2;
    public static final String AMOUNT_DESC_RECURRING = " " + PREFIX_AMOUNT + VALID_AMOUNT_RECURRING_2;
    public static final String CATEGORY_DESC_RECURRING = " " + PREFIX_CATEGORY + VALID_CATEGORY_RECURRING_2;
    public static final String DATE_DESC_RECURRING = " " + PREFIX_DATE + VALID_DATE_RECURRING_2;
    public static final String FREQUENCY_DESC_RECURRING = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_RECURRING_2;
    public static final String OCCURRENCE_DESC_RECURRING = " " + PREFIX_OCCURRENCE + VALID_OCCURRENCE_RECURRING_2;
    public static final String REMARKS_DESC_RECURRING = " " + PREFIX_REMARKS + VALID_REMARKS_RECURRING_2;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // ' ' not allowed as a name
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // 'a' not allowed in amount
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "fod"; // not one of enum values
    public static final String INVALID_DATE_DESC_FORMAT = " " + PREFIX_DATE + "142-121-012"; // must be dd-MM-yyyy
    public static final String INVALID_DEADLINE_DESC_FORMAT = " " + PREFIX_DUE
            + "12-1213-01"; // must be dd-MM-yyyy
    public static final String INVALID_DEADLINE_DESC_DATE = " " + PREFIX_DUE
            + "01-01-2018"; // cannot be before today's date
    public static final String INVALID_DATE_DESC_EXIST = " " + PREFIX_DATE + "29-02-2021"; //does not exist
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DUE + "12-1213-01"; // must be dd-MM-yyyy
    public static final String INVALID_STARTDATE_DESC_FORMAT = " " + PREFIX_STARTDATE + "29-033-2021"; //does not exist
    public static final String INVALID_ENDDATE_DESC_FORMAT = " " + PREFIX_ENDDATE + "291-02-23021"; //does not exist
    public static final String INVALID_STARTDATE_DESC_EXIST = " " + PREFIX_STARTDATE + "29-02-2019"; //does not exist
    public static final String INVALID_ENDDATE_DESC_EXIST = " " + PREFIX_ENDDATE + "31-06-2019"; //does not exist
    public static final String INVALID_STARTDATE_DESC_BEFORE_TODAY = " " + PREFIX_STARTDATE + "01-02-2019"; // past
    public static final String INVALID_ENDDATE_DESC_BEFORE_TODAY = " " + PREFIX_ENDDATE + "01-03-2019"; // past
    public static final String INVALID_FREQUENCY_DESC = " " + PREFIX_FREQUENCY + "z"; // 'z' not allowed as a name
    public static final String INVALID_OCCURRENCE_DESC = " " + PREFIX_OCCURRENCE + "a";
    // 'a' not allowed as a occurrence

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditExpenseCommand.EditExpenseDescriptor DESC_EXPENSE;
    public static final EditBudgetCommand.EditBudgetDescriptor DESC_BUDGET;
    public static final EditRecurringCommand.EditRecurringDescriptor DESC_RECURRING;

    static {
        DESC_EXPENSE = new EditExpenseDescriptorBuilder().withName(VALID_NAME_EXPENSE)
                .withAmount(VALID_AMOUNT_EXPENSE).withCategory(VALID_CATEGORY_EXPENSE).withDate(VALID_DATE_EXPENSE)
                .withRemarks(VALID_REMARKS_DEBT).build();
        DESC_BUDGET = new EditBudgetDescriptorBuilder().withAmount(VALID_AMOUNT_BUDGET)
                .withStartDate(VALID_STARTDATE_BUDGET).withEndDate(VALID_ENDDATE_BUDGET)
                .withRemarks(VALID_REMARKS_BUDGET).build();
        DESC_RECURRING = new EditRecurringDescriptorBuilder().withName(VALID_NAME_RECURRING)
                .withAmount(VALID_AMOUNT_RECURRING).withCategory(VALID_CATEGORY_RECURRING)
                .withDate(VALID_DATE_RECURRING)
                .withRemarks(VALID_REMARKS_RECURRING).withFrequency(VALID_FREQUENCY_RECURRING)
                .withOccurrence(VALID_OCCURRENCE_RECURRING).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered expense list and selected expense in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinanceTracker expectedFinanceTracker = new FinanceTracker(actualModel.getFinanceTracker());
        List<Expense> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenseList());
        Expense expectedSelectedExpense = actualModel.getSelectedExpense();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedFinanceTracker, actualModel.getFinanceTracker());
            assertEquals(expectedFilteredList, actualModel.getFilteredExpenseList());
            assertEquals(expectedSelectedExpense, actualModel.getSelectedExpense());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    //==========Expense-related==============================================================================

    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenseList().size());

        Expense expense = model.getFilteredExpenseList().get(targetIndex.getZeroBased());
        final String[] splitName = expense.getName().name.split("\\s+");
        model.updateFilteredExpenseList(new NameContainsKeywordsPredicateForExpense(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExpenseList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the recurring at the given {@code targetIndex} in the
     * {@code model}'s recurring list.
     */
    public static void showRecurringAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecurringList().size());

        Recurring recurring = model.getFilteredRecurringList().get(targetIndex.getZeroBased());
        final String[] splitName = recurring.getName().name.split("\\s+");
        model.updateFilteredRecurringList(new RecurringNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecurringList().size());
    }

    /**
     * Deletes the first expense in {@code model}'s filtered list from {@code model}'s finance tracker.
     */
    public static void deleteFirstExpense(Model model) {
        Expense firstExpense = model.getFilteredExpenseList().get(0);
        model.deleteExpense(firstExpense);
        model.commitFinanceTracker();
    }

    //==========Debt-related=================================================================================

    /**
     * Updates {@code model}'s filtered list to show only the debt at the given {@code targetIndex} in the
     * {@code model}'s finance tracker.
     */
    public static void showDebtAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDebtList().size());

        Debt debt = model.getFilteredDebtList().get(targetIndex.getZeroBased());
        final String[] splitName = debt.getPersonOwed().name.split("\\s+");
        model.updateFilteredDebtList(new NameContainsKeywordsPredicateForDebt(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDebtList().size());
    }

    /**
     * Deletes the first debt in {@code model}'s filtered list from {@code model}'s finance tracker.
     */
    public static void deleteFirstDebt(Model model) {
        Debt firstDebt = model.getFilteredDebtList().get(0);
        model.deleteDebt(firstDebt);
        model.commitFinanceTracker();
    }

}
