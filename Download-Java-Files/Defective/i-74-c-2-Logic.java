package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

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
    void setSelectedPerson(Expense expense);
}
