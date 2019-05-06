package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetNotFoundException;
import seedu.address.model.debt.Debt;
import seedu.address.model.debt.exceptions.DebtNotFoundException;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.recurring.exceptions.RecurringNotFoundException;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.exceptions.StatisticsNotFoundException;


/**
 * Represents the in-memory model of the finance tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinanceTracker versionedFinanceTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Debt> filteredDebts;
    private final FilteredList<Recurring> filteredRecurring;
    private final FilteredList<Budget> filteredBudgets;
    private List<Expense> unfilteredExpenses;
    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Debt> selectedDebt = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Recurring> selectedRecurring = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Statistics> statistics = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given financeTracker and userPrefs.
     */
    public ModelManager(ReadOnlyFinanceTracker financeTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(financeTracker, userPrefs);

        logger.fine("Initializing with finance tracker: " + financeTracker + " and user prefs " + userPrefs);

        versionedFinanceTracker = new VersionedFinanceTracker(financeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(versionedFinanceTracker.getExpenseList());
        filteredExpenses.addListener(this::ensureSelectedExpenseIsValid);
        filteredDebts = new FilteredList<>(versionedFinanceTracker.getDebtList());
        filteredDebts.addListener(this::ensureSelectedDebtIsValid);
        filteredBudgets = new FilteredList<>(versionedFinanceTracker.getBudgetList());
        filteredBudgets.addListener(this::ensureSelectedBudgetIsValid);
        filteredRecurring = new FilteredList<>(versionedFinanceTracker.getRecurringList());
        filteredRecurring.addListener(this::ensureSelectedRecurringIsValid);
        unfilteredExpenses = financeTracker.getExpenseList();
    }

    public ModelManager() {
        this(new FinanceTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        return userPrefs.getFinanceTrackerFilePath();
    }

    @Override
    public void setFinanceTrackerFilePath(Path financeTrackerFilePath) {
        requireNonNull(financeTrackerFilePath);
        userPrefs.setFinanceTrackerFilePath(financeTrackerFilePath);
    }

    //=========== FinanceTracker ================================================================================

    @Override
    public void setFinanceTracker(ReadOnlyFinanceTracker financeTracker) {
        versionedFinanceTracker.resetData(financeTracker);
    }

    @Override
    public ReadOnlyFinanceTracker getFinanceTracker() {
        return versionedFinanceTracker;
    }

    //=========== Statistics ====================================================================================

    @Override
    public void calculateStatistics(String command, Date date1, Date date2, Frequency frequency) {

        FilteredList<Expense> statsExpenses = new FilteredList<>(versionedFinanceTracker.getExpenseList());
        Statistics statistics = new Statistics(statsExpenses);
        statistics.calculateStats(command, date1, date2, frequency);

        this.setStatistics(statistics);
    }

    @Override
    public ReadOnlyProperty<Statistics> statisticsProperty() {
        return statistics;
    }

    @Override
    public void setStatistics(Statistics statistics) {
        if (statistics == null) {
            throw new StatisticsNotFoundException();
        }
        this.statistics.setValue(statistics);
    }

    //=========== Expenses ======================================================================================

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return versionedFinanceTracker.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        versionedFinanceTracker.removeExpense(target);
        // update totalSpent of budget of respective category
        int index = -1;
        for (Budget budget : filteredBudgets) {
            if (budget.getCategory() == target.getCategory()) {
                index = filteredBudgets.indexOf(budget);
            }
        }
        if (index != -1) {
            Budget targetBudget = filteredBudgets.get(index);
            if (target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate())) {
                return;
            }
            Budget updatedBudget = new Budget(filteredBudgets.get(index));
            double diff = 0 - target.getAmount().value;
            updatedBudget.updateTotalSpent(diff);
            updatedBudget.updatePercentage();
            versionedFinanceTracker.setBudget(targetBudget, updatedBudget);
        }
    }

    @Override
    public void addExpense(Expense expense) {
        versionedFinanceTracker.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        // update totalSpent of budget of respective category
        unfilteredExpenses = versionedFinanceTracker.getExpenseList();
        int index = -1;
        for (Budget budget : filteredBudgets) {
            if (budget.getCategory() == expense.getCategory()) {
                index = filteredBudgets.indexOf(budget);
            }
        }
        if (index != -1) {
            Budget targetBudget = filteredBudgets.get(index);
            if (expense.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || expense.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate())) {
                return;
            }
            Budget updatedBudget = new Budget(filteredBudgets.get(index));
            updatedBudget.updateTotalSpent(expense.getAmount().value);
            updatedBudget.updatePercentage();
            versionedFinanceTracker.setBudget(targetBudget, updatedBudget);
        }
        updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);
        versionedFinanceTracker.setExpense(target, editedExpense);
        // update totalSpent of budget of respective category
        unfilteredExpenses = versionedFinanceTracker.getExpenseList();
        // identify budget of category of target
        int index = -1;
        for (Budget budget : filteredBudgets) {
            if (budget.getCategory() == target.getCategory()) {
                index = filteredBudgets.indexOf(budget);
                break;
            }
        }
        // identify budget of category of editedExpense
        int index2 = -1;
        for (Budget budget : filteredBudgets) {
            if (budget.getCategory() == editedExpense.getCategory()) {
                index2 = filteredBudgets.indexOf(budget);
                break;
            }
        }

        if ((index == -1 && index2 == -1) || (target.getAmount() == editedExpense.getAmount())
                && target.getCategory() == editedExpense.getCategory()
                && target.getDate() == editedExpense.getDate()) {
            return;
        }

        Budget targetBudget = filteredBudgets.get(index);
        Budget updatedBudget = new Budget(filteredBudgets.get(index));
        Budget targetBudget2 = filteredBudgets.get(index2);
        Budget updatedBudget2 = new Budget(filteredBudgets.get(index2));

        // case where only amount is edited.
        if (target.getAmount() != editedExpense.getAmount()
                && target.getCategory() == editedExpense.getCategory()
                && target.getDate() == editedExpense.getDate()) {
            double diff = editedExpense.getAmount().value - target.getAmount().value;
            updatedBudget.updateTotalSpent(diff);
            updatedBudget.updatePercentage();
        }

        // date changed, category unchanged.
        if (target.getDate() != editedExpense.getDate() && target.getCategory() == editedExpense.getCategory()) {
            // target date and editedExpense date both not within budget's time frame
            if ((target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))
                    && (editedExpense.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || editedExpense.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))) {
                return;
            }
            // target date not within budget time frame but editedExpense date is, add to totalSpent of updatedBudget2
            if ((target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))
                    && !(editedExpense.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || editedExpense.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))) {
                updatedBudget.updateTotalSpent(editedExpense.getAmount().value);
                updatedBudget.updatePercentage();
            } else if (!(target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))
                    && (editedExpense.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || editedExpense.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))) {
                // target date is within budget time frame but editedExpense date is no longer within budget's duration
                updatedBudget.updateTotalSpent(0 - target.getAmount().value);
                updatedBudget.updatePercentage();
            } else if (!(target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))
                    && !(editedExpense.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                    || editedExpense.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))) {
                // target date and editedExpense date are both within budget time frame
                double diff = editedExpense.getAmount().value - target.getAmount().value;
                updatedBudget.updateTotalSpent(diff);
                updatedBudget.updatePercentage();
            }
        }

        // category of expense changed
        if (target.getCategory() != editedExpense.getCategory()) {
            if (index != -1) {
                // check if target date is within budget1 time frame
                if (!(target.getDate().getLocalDate().isBefore(targetBudget.getStartDate().getLocalDate())
                        || target.getDate().getLocalDate().isAfter(targetBudget.getEndDate().getLocalDate()))) {
                    updatedBudget.updateTotalSpent(0 - target.getAmount().value);
                    updatedBudget.updatePercentage();
                }
            }
            if (index2 != -1) {
                if (!(editedExpense.getDate().getLocalDate().isBefore(targetBudget2.getStartDate().getLocalDate())
                        || editedExpense.getDate().getLocalDate().isAfter(targetBudget2.getEndDate().getLocalDate()))) {
                    updatedBudget2.updateTotalSpent(editedExpense.getAmount().value);
                    updatedBudget2.updatePercentage();
                }
            }
        }
        versionedFinanceTracker.setBudget(targetBudget, updatedBudget);
        versionedFinanceTracker.setBudget(targetBudget2, updatedBudget2);
    }

    //=========== Debts ========================================================================================

    @Override
    public boolean hasDebt(Debt debt) {
        requireNonNull(debt);
        return versionedFinanceTracker.hasDebt(debt);
    }

    @Override
    public void deleteDebt(Debt target) {
        versionedFinanceTracker.removeDebt(target);
    }

    @Override
    public void addDebt(Debt debt) {
        versionedFinanceTracker.addDebt(debt);
        updateFilteredDebtList(PREDICATE_SHOW_ALL_DEBTS);
    }

    @Override
    public void setDebt(Debt target, Debt editedDebt) {
        requireAllNonNull(target, editedDebt);

        versionedFinanceTracker.setDebt(target, editedDebt);
    }

    //=========== Budgets ========================================================================================

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return versionedFinanceTracker.hasBudget(budget);
    }

    @Override
    public void deleteBudget(Budget target) {
        versionedFinanceTracker.removeBudget(target);
    }

    @Override
    public void addBudget(Budget budget) {
        unfilteredExpenses = versionedFinanceTracker.getExpenseList();
        int sum = 0;
        for (Expense expense : unfilteredExpenses) {
            if (expense.getCategory() == budget.getCategory()
                    && !(expense.getDate().getLocalDate().isBefore(budget.getStartDate().getLocalDate()))
                    && !(expense.getDate().getLocalDate().isAfter(budget.getEndDate().getLocalDate()))) {
                sum += expense.getAmount().value;
            }
        }
        budget.setTotalSpent(sum);
        budget.updatePercentage();
        versionedFinanceTracker.addBudget(budget);
        updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        editedBudget.setTotalSpent(0);
        unfilteredExpenses = versionedFinanceTracker.getExpenseList();
        int sum = 0;
        for (Expense expense : unfilteredExpenses) {
            if (expense.getCategory() == editedBudget.getCategory()
                    && !(expense.getDate().getLocalDate().isBefore(editedBudget.getStartDate().getLocalDate()))
                    && !(expense.getDate().getLocalDate().isAfter(editedBudget.getEndDate().getLocalDate()))) {
                sum += expense.getAmount().value;
            }
        }
        editedBudget.setTotalSpent(sum);
        editedBudget.updatePercentage();
        versionedFinanceTracker.setBudget(target, editedBudget);
    }

    //=========== Recurrings ========================================================================================

    @Override
    public boolean hasRecurring(Recurring recurring) {
        requireNonNull(recurring);
        return versionedFinanceTracker.hasRecurring(recurring);
    }

    @Override
    public void deleteRecurring(Recurring target) {
        versionedFinanceTracker.removeRecurring(target);
    }

    @Override
    public void addRecurring(Recurring recurring) {
        versionedFinanceTracker.addRecurring(recurring);
        updateFilteredRecurringList(PREDICATE_SHOW_ALL_RECURRING);
    }

    @Override
    public void setRecurring(Recurring target, Recurring editedRecurring) {
        requireAllNonNull(target, editedRecurring);

        versionedFinanceTracker.setRecurring(target, editedRecurring);
    }

    //=========== Filtered Expense List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedFinanceTracker}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    //=========== Filtered Debt List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Debt} backed by the internal list of
     * {@code versionedFinanceTracker}
     */
    @Override
    public ObservableList<Debt> getFilteredDebtList() {
        return filteredDebts;
    }

    @Override
    public void updateFilteredDebtList(Predicate<Debt> predicate) {
        requireNonNull(predicate);
        filteredDebts.setPredicate(predicate);
    }

    //=========== Filtered Budget List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Budget} backed by the internal list of
     * {@code versionedFinanceTracker}
     */
    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudgets.setPredicate(predicate);
    }

    //=========== Filtered Recurring List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recurring} backed by the internal list of
     * {@code versionedFinanceTracker}
     */
    @Override
    public ObservableList<Recurring> getFilteredRecurringList() {
        return filteredRecurring;
    }

    @Override
    public void updateFilteredRecurringList(Predicate<Recurring> predicate) {
        requireNonNull(predicate);
        filteredRecurring.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoFinanceTracker() {
        return versionedFinanceTracker.canUndo();
    }

    @Override
    public boolean canRedoFinanceTracker() {
        return versionedFinanceTracker.canRedo();
    }

    @Override
    public void undoFinanceTracker() {
        versionedFinanceTracker.undo();
    }

    @Override
    public void redoFinanceTracker() {
        versionedFinanceTracker.redo();
    }

    @Override
    public void commitFinanceTracker() {
        versionedFinanceTracker.commit();
    }

    //=========== Selected expense ===========================================================================

    @Override
    public ReadOnlyProperty<Expense> selectedExpenseProperty() {
        return selectedExpense;
    }

    @Override
    public Expense getSelectedExpense() {
        return selectedExpense.getValue();
    }

    @Override
    public void setSelectedExpense(Expense expense) {
        if (expense != null && !filteredExpenses.contains(expense)) {
            throw new ExpenseNotFoundException();
        }
        selectedExpense.setValue(expense);
    }

    //=========== Selected budget ===========================================================================
    @Override
    public ReadOnlyProperty<Budget> selectedBudgetProperty() {
        return selectedBudget;
    }

    @Override
    public Budget getSelectedBudget() {
        return selectedBudget.getValue();
    }

    @Override
    public void setSelectedBudget(Budget budget) {
        if (budget != null && !filteredBudgets.contains(budget)) {
            throw new BudgetNotFoundException();
        }
        selectedBudget.setValue(budget);
    }

    //=========== Selected debt ===========================================================================
    @Override
    public ReadOnlyProperty<Debt> selectedDebtProperty() {
        return selectedDebt;
    }

    @Override
    public Debt getSelectedDebt() {
        return selectedDebt.getValue();
    }

    @Override
    public void setSelectedDebt(Debt debt) {
        if (debt != null && !filteredDebts.contains(debt)) {
            throw new DebtNotFoundException();
        }
        selectedDebt.setValue(debt);
    }

    @Override
    public ReadOnlyProperty<Recurring> selectedRecurringProperty() {
        return selectedRecurring;
    }

    @Override
    public Recurring getSelectedRecurring() {
        return selectedRecurring.getValue();
    }

    @Override
    public void setSelectedRecurring(Recurring recurring) {
        if (recurring != null && !filteredRecurring.contains(recurring)) {
            throw new RecurringNotFoundException();
        }
        selectedRecurring.setValue(recurring);
    }

    /**
     * Ensures {@code selectedRecurring} is a valid expense in {@code filteredRecurring}.
     */
    private void ensureSelectedRecurringIsValid(ListChangeListener.Change<? extends Recurring> change) {
        while (change.next()) {
            if (selectedRecurring.getValue() == null) {
                // null is always a valid selected recurring, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedRecurringReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedRecurring.getValue());
            if (wasSelectedRecurringReplaced) {
                // Update selectedRecurring to its new value.
                int index = change.getRemoved().indexOf(selectedRecurring.getValue());
                selectedRecurring.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRecurringRemoved = change.getRemoved().stream()
                    .anyMatch(removedRecurring -> selectedRecurring.getValue().isSameRecurring(removedRecurring));
            if (wasSelectedRecurringRemoved) {
                // Select the recurring that came before it in the list,
                // or clear the selection if there is no such recurring.
                selectedRecurring.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedBudget} is a valid expense in {@code filteredBudget}.
     */
    private void ensureSelectedBudgetIsValid(ListChangeListener.Change<? extends Budget> change) {
        while (change.next()) {
            if (selectedBudget.getValue() == null) {
                // null is always a valid selected budget, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedBudgetReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedBudget.getValue());
            if (wasSelectedBudgetReplaced) {
                // Update selectedBudget to its new value.
                int index = change.getRemoved().indexOf(selectedBudget.getValue());
                selectedBudget.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedBudgetRemoved = change.getRemoved().stream()
                    .anyMatch(removedBudget -> selectedBudget.getValue().equals(removedBudget));
            if (wasSelectedBudgetRemoved) {
                // Select the budget that came before it in the list,
                // or clear the selection if there is no such budget.
                selectedBudget.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedDebt} is a valid debt in {@code filteredDebts}.
     */
    private void ensureSelectedDebtIsValid(ListChangeListener.Change<? extends Debt> change) {
        while (change.next()) {
            if (selectedDebt.getValue() == null) {
                // null is always a valid selected debt, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedDebtReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedDebt.getValue());
            if (wasSelectedDebtReplaced) {
                // Update selectedDebt to its new value.
                int index = change.getRemoved().indexOf(selectedDebt.getValue());
                selectedDebt.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedDebtRemoved = change.getRemoved().stream()
                    .anyMatch(removedDebt -> selectedDebt.getValue().isSameDebt(removedDebt));
            if (wasSelectedDebtRemoved) {
                // Select the debt that came before it in the list,
                // or clear the selection if there is no such debt.
                selectedDebt.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedExpense} is a valid expense in {@code filteredExpenses}.
     */
    private void ensureSelectedExpenseIsValid(ListChangeListener.Change<? extends Expense> change) {
        while (change.next()) {
            if (selectedExpense.getValue() == null) {
                // null is always a valid selected expense, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedExpenseReplaced = change.wasReplaced()
                    && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedExpense.getValue());
            if (wasSelectedExpenseReplaced) {
                // Update selectedExpense to its new value.
                int index = change.getRemoved().indexOf(selectedExpense.getValue());
                selectedExpense.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedExpenseRemoved = change.getRemoved().stream()
                    .anyMatch(removedExpense -> selectedExpense.getValue().isSameExpense(removedExpense));
            if (wasSelectedExpenseRemoved) {
                // Select the expense that came before it in the list,
                // or clear the selection if there is no such expense.
                selectedExpense.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedFinanceTracker.equals(other.versionedFinanceTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenses.equals(other.filteredExpenses)
                && filteredDebts.equals(other.filteredDebts)
                && filteredRecurring.equals(other.filteredRecurring)
                && filteredBudgets.equals(other.filteredBudgets)
                && Objects.equals(selectedExpense.get(), other.selectedExpense.get())
                && Objects.equals(selectedDebt.get(), other.selectedDebt.get())
                && Objects.equals(selectedRecurring.get(), other.selectedRecurring.get())
                && Objects.equals(selectedBudget.get(), other.selectedBudget.get());
    }

}
