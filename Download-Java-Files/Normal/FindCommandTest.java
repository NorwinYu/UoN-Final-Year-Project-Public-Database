package seedu.address.logic.commands.generalcommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_FOUND_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.DOCTOR;
import static seedu.address.testutil.TypicalExpenses.GROCERIES;
import static seedu.address.testutil.TypicalExpenses.TAXI;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.NameContainsKeywordsPredicateForExpense;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTrackerWithExpenses(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private final Comparator<Expense> comparator = new Comparator<Expense>() {
        @Override
        public int compare(Expense o1, Expense o2) {
            return o2.getDate().compareTo(o1.getDate()); // in descending order
        }
    };

    @Test
    public void equals() {
        NameContainsKeywordsPredicateForExpense firstPredicate =
                new NameContainsKeywordsPredicateForExpense(Collections.singletonList("first"));
        NameContainsKeywordsPredicateForExpense secondPredicate =
                new NameContainsKeywordsPredicateForExpense(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_FOUND_OVERVIEW, 0);
        NameContainsKeywordsPredicateForExpense predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredExpenseList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_FOUND_OVERVIEW, 3);
        NameContainsKeywordsPredicateForExpense predicate = preparePredicate("TAXI GROCERIES DOCTOR ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        ArrayList<Expense> inputList = new ArrayList<>(Arrays.asList(TAXI, GROCERIES, DOCTOR));
        Collections.sort(inputList, comparator); // sort in descending order, then only compare

        assertEquals(inputList, model.getFilteredExpenseList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicateForExpense}.
     */
    private NameContainsKeywordsPredicateForExpense preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicateForExpense(Arrays.asList(userInput.split("\\s+")));
    }
}
