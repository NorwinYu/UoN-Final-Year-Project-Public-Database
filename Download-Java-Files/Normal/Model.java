package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /** {@code Predicate} that always evaluate to true if it is within the range of current date */
    // needs to be implemented
    Predicate<Expense> PREDICATE_SHOW_DAY_EXPENSES = p -> p.getDate().isWithinDurationBeforeToday("day");
    Predicate<Expense> PREDICATE_SHOW_WEEK_EXPENSES = p -> p.getDate().isWithinDurationBeforeToday("week");
    Predicate<Expense> PREDICATE_SHOW_MONTH_EXPENSES = p -> p.getDate().isWithinDurationBeforeToday("month");
    Predicate<Expense> PREDICATE_SHOW_YEAR_EXPENSES = p -> p.getDate().isWithinDurationBeforeToday("year");

    /** {@code Predicate} that always evaluate to true if expense is in the specified category */
    Predicate<Expense> PREDICATE_SHOW_FOOD_EXPENSES = p -> p.getCategory().equals(Category.FOOD);
    Predicate<Expense> PREDICATE_SHOW_WORK_EXPENSES = p -> p.getCategory().equals(Category.WORK);
    Predicate<Expense> PREDICATE_SHOW_TRAVEL_EXPENSES = p -> p.getCategory().equals(Category.TRAVEL);
    Predicate<Expense> PREDICATE_SHOW_OTHERS_EXPENSES = p -> p.getCategory().equals(Category.OTHERS);
    Predicate<Expense> PREDICATE_SHOW_ENT_EXPENSES = p -> p.getCategory().equals(Category.ENTERTAINMENT);
    Predicate<Expense> PREDICATE_SHOW_SHOPPING_EXPENSES = p -> p.getCategory().equals(Category.SHOPPING);
    Predicate<Expense> PREDICATE_SHOW_TRANSPORT_EXPENSES = p -> p.getCategory().equals(Category.TRANSPORT);
    Predicate<Expense> PREDICATE_SHOW_UTIL_EXPENSES = p -> p.getCategory().equals(Category.UTILITIES);
    Predicate<Expense> PREDICATE_SHOW_HEALTHCARE_EXPENSES = p -> p.getCategory().equals(Category.HEALTHCARE);

    /**
     * {@code Predicate} that always evaluate to true if expense amount is within range, note that the range
     *  is in terms of cents
     **/
    Predicate<Expense> PREDICATE_SHOW_AMOUNT_OVER_10_EXPENSES = p -> p.getAmount().value >= 1000;
    Predicate<Expense> PREDICATE_SHOW_AMOUNT_OVER_100_EXPENSES = p -> p.getAmount().value >= 10000;
    Predicate<Expense> PREDICATE_SHOW_AMOUNT_OVER_1000_EXPENSES = p -> p.getAmount().value >= 100000;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Debt> PREDICATE_SHOW_ALL_DEBTS = unused -> true;

    /** {@code Predicate} that always evaluate to true if deadline is within range */
    // needs to be implemented
    Predicate<Debt> PREDICATE_SHOW_DAY_DEBTS = p -> p.getDeadline().isWithinDurationAfterToday("day");
    Predicate<Debt> PREDICATE_SHOW_WEEK_DEBTS = p -> p.getDeadline().isWithinDurationAfterToday("week");
    Predicate<Debt> PREDICATE_SHOW_MONTH_DEBTS = p -> p.getDeadline().isWithinDurationAfterToday("month");
    Predicate<Debt> PREDICATE_SHOW_YEAR_DEBTS = p -> p.getDeadline().isWithinDurationAfterToday("year");

    /** {@code Predicate} that always evaluate to true if expense is in the specified category */
    Predicate<Debt> PREDICATE_SHOW_FOOD_DEBTS = p -> p.getCategory().equals(Category.FOOD);
    Predicate<Debt> PREDICATE_SHOW_WORK_DEBTS = p -> p.getCategory().equals(Category.WORK);
    Predicate<Debt> PREDICATE_SHOW_TRAVEL_DEBTS = p -> p.getCategory().equals(Category.TRAVEL);
    Predicate<Debt> PREDICATE_SHOW_OTHERS_DEBTS = p -> p.getCategory().equals(Category.OTHERS);
    Predicate<Debt> PREDICATE_SHOW_ENT_DEBTS = p -> p.getCategory().equals(Category.ENTERTAINMENT);
    Predicate<Debt> PREDICATE_SHOW_SHOPPING_DEBTS = p -> p.getCategory().equals(Category.SHOPPING);
    Predicate<Debt> PREDICATE_SHOW_TRANSPORT_DEBTS = p -> p.getCategory().equals(Category.TRANSPORT);
    Predicate<Debt> PREDICATE_SHOW_UTIL_DEBTS = p -> p.getCategory().equals(Category.UTILITIES);
    Predicate<Debt> PREDICATE_SHOW_HEALTHCARE_DEBTS = p -> p.getCategory().equals(Category.HEALTHCARE);

    /**
     * {@code Predicate} that always evaluate to true if debt amount is within range, note that the range
     *  is in terms of cents
     **/
    Predicate<Debt> PREDICATE_SHOW_AMOUNT_OVER_10_DEBTS = p -> p.getAmount().value >= 1000;
    Predicate<Debt> PREDICATE_SHOW_AMOUNT_OVER_100_DEBTS = p -> p.getAmount().value >= 10000;
    Predicate<Debt> PREDICATE_SHOW_AMOUNT_OVER_1000_DEBTS = p -> p.getAmount().value >= 100000;


    /** {@code Predicate} that always evaluate to true */
    Predicate<Recurring> PREDICATE_SHOW_ALL_RECURRING = unused -> true;

    /** {@code Predicate} that always evaluate to true if it is within the range of current date */
    // needs to be implemented
    Predicate<Recurring> PREDICATE_SHOW_DAY_RECURRING = p -> p.getDate().isWithinDurationBeforeToday("day");
    Predicate<Recurring> PREDICATE_SHOW_WEEK_RECURRING = p -> p.getDate().isWithinDurationBeforeToday("week");
    Predicate<Recurring> PREDICATE_SHOW_MONTH_RECURRING = p -> p.getDate().isWithinDurationBeforeToday("month");
    Predicate<Recurring> PREDICATE_SHOW_YEAR_RECURRING = p -> p.getDate().isWithinDurationBeforeToday("year");

    /** {@code Predicate} that always evaluate to true if expense is in the specified category */
    Predicate<Recurring> PREDICATE_SHOW_FOOD_RECURRING = p -> p.getCategory().equals(Category.FOOD);
    Predicate<Recurring> PREDICATE_SHOW_WORK_RECURRING = p -> p.getCategory().equals(Category.WORK);
    Predicate<Recurring> PREDICATE_SHOW_TRAVEL_RECURRING = p -> p.getCategory().equals(Category.TRAVEL);
    Predicate<Recurring> PREDICATE_SHOW_OTHERS_RECURRING = p -> p.getCategory().equals(Category.OTHERS);
    Predicate<Recurring> PREDICATE_SHOW_ENT_RECURRING = p -> p.getCategory().equals(Category.ENTERTAINMENT);
    Predicate<Recurring> PREDICATE_SHOW_SHOPPING_RECURRING = p -> p.getCategory().equals(Category.SHOPPING);
    Predicate<Recurring> PREDICATE_SHOW_TRANSPORT_RECURRING = p -> p.getCategory().equals(Category.TRANSPORT);
    Predicate<Recurring> PREDICATE_SHOW_UTIL_RECURRING = p -> p.getCategory().equals(Category.UTILITIES);
    Predicate<Recurring> PREDICATE_SHOW_HEALTHCARE_RECURRING = p -> p.getCategory().equals(Category.HEALTHCARE);

    /**
     * {@code Predicate} that always evaluate to true if recurring amount is within range, note that the range
     *  is in terms of cents
     **/
    Predicate<Recurring> PREDICATE_SHOW_AMOUNT_OVER_10_RECURRING = p -> p.getAmount().value >= 1000;
    Predicate<Recurring> PREDICATE_SHOW_AMOUNT_OVER_100_RECURRING = p -> p.getAmount().value >= 10000;
    Predicate<Recurring> PREDICATE_SHOW_AMOUNT_OVER_1000_RECURRING = p -> p.getAmount().value >= 100000;


    /** {@code Predicate} that always evaluate to true */
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' finance tracker file path.
     */
    Path getFinanceTrackerFilePath();

    /**
     * Sets the user prefs' finance tracker file path.
     */
    void setFinanceTrackerFilePath(Path financeTrackerFilePath);

    /**
     * Replaces finance tracker data with the data in {@code financeTracker}.
     */
    void setFinanceTracker(ReadOnlyFinanceTracker financeTracker);

    /** Returns the FinanceTracker */
    ReadOnlyFinanceTracker getFinanceTracker();

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the finance tracker.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the finance tracker.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the finance tracker.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the finance tracker.
     * The expense identity of {@code editedExpense} must not be the same as another existing
     * expense in the finance tracker.
     */
    void setExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    /**
     * Returns true if a debt with the same identity as {@code debt} exists in the finance tracker.
     */
    boolean hasDebt(Debt debt);

    /**
     * Deletes the given debt.
     * The debt must exist in the finance tracker.
     */
    void deleteDebt(Debt target);

    /**
     * Adds the given debt.
     */
    void addDebt(Debt debt);

    /**
     * Replaces the given debt {@code target} with {@code editedDebt}.
     * {@code target} must exist in the finance tracker.
     * The expense identity of {@code editedDebt} must not be the same as another existing debt in the finance tracker.
     */
    void setDebt(Debt target, Debt editedDebt);

    /** Returns an unmodifiable view of the filtered debt list */
    ObservableList<Debt> getFilteredDebtList();

    /**
     * Updates the filter of the filtered debt list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDebtList(Predicate<Debt> predicate);

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the finance tracker.
     */
    boolean hasBudget(Budget budget);

    /**
     * Deletes the given budget.
     * The budget must exist in the finance tracker.
     */
    void deleteBudget(Budget target);

    /**
     * Adds the given budget.
     * {@code budget} must not overlap with an existing budget in the finance tracker.
     */
    void addBudget(Budget budget);

    /**
     * Replaces the given budget {@code target} with {@code editedBudget}.
     * {@code target} must exist in the finance tracker.
     * The budget identity of {@code editedBudget} must not be the same as another existing
     * budget in the finance tracker.
     */
    void setBudget(Budget target, Budget editedBudget);

    /** Returns an unmodifiable view of the filtered budget list */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Updates the filter of the filtered budget list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBudgetList(Predicate<Budget> predicate);

    /**
     * Returns true if a recurring with the same identity as {@code recurring} exists in the finance tracker.
     */
    boolean hasRecurring(Recurring recurring);

    /**
     * Deletes the given recurring.
     * The recurring must exist in the finance tracker.
     */
    void deleteRecurring(Recurring target);

    /**
     * Adds the given recurring.
     */
    void addRecurring(Recurring recurring);

    /**
     * Replaces the given recurring {@code target} with {@code editedRecurring}.
     * {@code target} must exist in the finance tracker.
     * The recurring identity of {@code editedRecurring} must not be the same as another existing
     * recurring in the finance tracker.
     */
    void setRecurring(Recurring target, Recurring editedRecurring);

    /** Returns an unmodifiable view of the filtered recurring list */
    ObservableList<Recurring> getFilteredRecurringList();

    /**
     * Updates the filter of the filtered recurring list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecurringList(Predicate<Recurring> predicate);

    /**
     * Returns true if the model has previous finance tracker states to restore.
     */
    boolean canUndoFinanceTracker();

    /**
     * Returns true if the model has undone finance tracker states to restore.
     */
    boolean canRedoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previous state.
     */
    void undoFinanceTracker();

    /**
     * Restores the model's finance tracker to its previously undone state.
     */
    void redoFinanceTracker();

    /**
     * Saves the current finance tracker state for undo/redo.
     */
    void commitFinanceTracker();

    /**
     * Selected expense in the filtered expense list.
     * null if no expense is selected.
     */
    ReadOnlyProperty<Expense> selectedExpenseProperty();

    /**
     * Returns the selected expense in the filtered expense list.
     * null if no expense is selected.
     */
    Expense getSelectedExpense();

    /**
     * Sets the selected expense in the filtered expense list.
     */
    void setSelectedExpense(Expense expense);

    /**
     * Selected debt in the filtered debt list.
     * null if no debt is selected.
     */
    ReadOnlyProperty<Debt> selectedDebtProperty();

    /**
     * Returns the selected debt in the filtered debt list.
     * null if no debt is selected.
     */
    Debt getSelectedDebt();

    /**
     * Sets the selected debt in the filtered debt list.
     */
    void setSelectedDebt(Debt debt);

    /**
     * Selected budget in the filtered budget list.
     * null if no budget is selected.
     */
    ReadOnlyProperty<Budget> selectedBudgetProperty();

    /**
     * Returns the selected budget in the filtered budget list.
     * null if no budget is selected.
     */
    Budget getSelectedBudget();

    /**
     * Sets the selected budget in the filtered budget list.
     */
    void setSelectedBudget(Budget budget);

    /**
     * Selected recurring in the filtered recurring list.
     * null if no recurring is selected.
     */
    ReadOnlyProperty<Recurring> selectedRecurringProperty();

    /**
     * Returns the selected recurring in the filtered recurring list.
     * null if no recurring is selected.
     */
    Recurring getSelectedRecurring();

    /**
     * Sets the selected recurring in the filtered recurring list.
     */
    void setSelectedRecurring(Recurring recurring);

    /**
     * Calculates statistics for the range {@code startDate} to {@code endDate}
     */
    void calculateStatistics(String command, Date date1, Date date2, Frequency frequency);

    /**
     * Selected Statistics
     * null if no Statistics has been generated.
     */
    ReadOnlyProperty<Statistics> statisticsProperty();

    /**
     * Sets the selected Statistics.
     */
    void setStatistics(Statistics statistics);
}
