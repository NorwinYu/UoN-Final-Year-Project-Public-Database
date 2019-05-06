package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FinanceTracker.
     *
     * @see seedu.address.model.Model#getFinanceTracker()
     */
    ReadOnlyFinanceTracker getFinanceTracker();

    /** Returns an unmodifiable view of the filtered list of expenses */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered list of debts */
    ObservableList<Debt> getFilteredDebtList();

    /** Returns an unmodifiable view of the filtered list of budgets */
    ObservableList<Budget> getFilteredBudgetList();

    /** Returns an unmodifiable view of the filtered list of recurring expenses */
    ObservableList<Recurring> getFilteredRecurringList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' Finance Tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected expense in the filtered expense list.
     * null if no expense is selected.
     *
     * @see seedu.address.model.Model#selectedExpenseProperty()
     */
    ReadOnlyProperty<Expense> selectedExpenseProperty();

    /**
     * Sets the selected expense in the filtered expense list.
     *
     * @see seedu.address.model.Model#setSelectedExpense(Expense)
     */
    void setSelectedExpense(Expense expense);

    /**
     * Selected debt in the filtered debt list.
     * null if no debt is selected.
     *
     * @see seedu.address.model.Model#selectedDebtProperty()
     */
    ReadOnlyProperty<Debt> selectedDebtProperty();

    /**
     * Sets the selected debt in the filtered expense list.
     *
     * @see seedu.address.model.Model#setSelectedDebt(Debt)
     */
    void setSelectedDebt(Debt debt);

    /**
     * Selected budget in the filtered budget list.
     * null if no budget is selected.
     *
     * @see seedu.address.model.Model#selectedBudgetProperty()
     */
    ReadOnlyProperty<Budget> selectedBudgetProperty();

    /**
     * Sets the selected budget in the filtered budget list.
     *
     * @see seedu.address.model.Model#setSelectedBudget(Budget)
     */
    void setSelectedBudget(Budget budget);

    /**
     * Selected recurring in the filtered recurring list.
     * null if no recurring is selected.
     *
     * @see seedu.address.model.Model#selectedRecurringProperty()
     */
    ReadOnlyProperty<Recurring> selectedRecurringProperty();

    /**
     * Sets the selected recurring in the filtered recurring list.
     *
     * @see seedu.address.model.Model#setSelectedRecurring(Recurring)
     */
    void setSelectedRecurring(Recurring recurring);

    /**
     * Selected Statistics
     * null if no Statistics has been generated.
     *
     * @see seedu.address.model.Model#statisticsProperty()
     */
    ReadOnlyProperty<Statistics> statisticsProperty();

    /**
     * Sets the selected statistics
     *
     * @see seedu.address.model.Model#setStatistics(Statistics)
     */
    void setStatistics(Statistics statistics);

}
