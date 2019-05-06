package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;
import seedu.address.testutil.RecurringBuilder;

public class AddRecurringCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddRecurringCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Recurring validRecurring = new RecurringBuilder().build();

        CommandResult commandResult = new AddRecurringCommand(validRecurring).execute(modelStub, commandHistory);

        assertEquals(String.format(AddRecurringCommand.MESSAGE_SUCCESS, validRecurring),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRecurring), modelStub.recurringsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Recurring alice = new RecurringBuilder().withName("Alice").build();
        Recurring bob = new RecurringBuilder().withName("Bob").build();
        AddRecurringCommand addAliceCommand = new AddRecurringCommand(alice);
        AddRecurringCommand addBobCommand = new AddRecurringCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddRecurringCommand addAliceCommandCopy = new AddRecurringCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recurring -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setStatistics(Statistics statistics) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Statistics> statisticsProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void calculateStatistics(String command, Date date1, Date date2, Frequency frequency) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFinanceTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        //==========Expense-related===========================================================================

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //==========Debt-related===========================================================================

        @Override
        public void addDebt(Debt debt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDebt(Debt debt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDebt(Debt target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDebt(Debt target, Debt editedDebt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Debt> getFilteredDebtList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDebtList(Predicate<Debt> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //==========Budget-related===========================================================================

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudget(Budget target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBudget(Budget target, Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //==========Recurring-related===========================================================================

        @Override
        public void addRecurring(Recurring recurring) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecurring(Recurring recurring) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecurring(Recurring target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecurring(Recurring target, Recurring editedRecurring) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Recurring> getFilteredRecurringList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecurringList(Predicate<Recurring> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //==========History-Control-related===================================================================

        @Override
        public boolean canUndoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFinanceTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Expense> selectedExpenseProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Expense getSelectedExpense() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Debt> selectedDebtProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Debt getSelectedDebt() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedDebt(Debt debt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Budget> selectedBudgetProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Budget getSelectedBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Recurring> selectedRecurringProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Recurring getSelectedRecurring() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedRecurring(Recurring recurring) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single recurring.
     */
    private class ModelStubWithPerson extends AddRecurringCommandTest.ModelStub {
        private final Recurring recurring;

        ModelStubWithPerson(Recurring recurring) {
            requireNonNull(recurring);
            this.recurring = recurring;
        }

        @Override
        public boolean hasRecurring(Recurring recurring) {
            requireNonNull(recurring);
            return this.recurring.isSameRecurring(recurring);
        }
    }

    /**
     * A Model stub that always accept the recurring being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddRecurringCommandTest.ModelStub {
        final ArrayList<Recurring> recurringsAdded = new ArrayList<>();

        @Override
        public boolean hasRecurring(Recurring recurring) {
            requireNonNull(recurring);
            return recurringsAdded.stream().anyMatch(recurring::isSameRecurring);
        }

        @Override
        public void addRecurring(Recurring recurring) {
            requireNonNull(recurring);
            recurringsAdded.add(recurring);
        }

        @Override
        public void commitFinanceTracker() {
            // called by {@code AddRecurringCommand#execute()}
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }
}
