package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
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
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetNotFoundException;
import seedu.address.model.debt.Debt;
import seedu.address.model.debt.exceptions.DebtNotFoundException;
import seedu.address.model.expense.Expense;
import seedu.address.model.person.exceptions.ExpenseNotFoundException;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.recurring.RecurringNotFoundException;


/**
 * Represents the in-memory model of the finance tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedFinanceTracker versionedFinanceTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Debt> filteredDebts;
    private final FilteredList<Recurring> filteredRecurrings;
    private final FilteredList<Budget> filteredBudgets;
    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Debt> selectedDebt = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Recurring> selectedRecurring = new SimpleObjectProperty<>();
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
        filteredRecurrings = new FilteredList<>(versionedFinanceTracker.getRecurringList());
        filteredRecurrings.addListener(this::ensureSelectedRecurringIsValid);
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

    //=========== Expenses ======================================================================================

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return versionedFinanceTracker.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        versionedFinanceTracker.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedFinanceTracker.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        versionedFinanceTracker.setExpense(target, editedExpense);
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
        versionedFinanceTracker.addBudget(budget);
        updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);

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
        return filteredRecurrings;
    }

    @Override
    public void updateFilteredRecurringList(Predicate<Recurring> predicate) {
        requireNonNull(predicate);
        filteredRecurrings.setPredicate(predicate);
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
        if (recurring != null && !filteredRecurrings.contains(recurring)) {
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
                    .anyMatch(removedBudget -> selectedBudget.getValue().isSameBudget(removedBudget));
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
                && Objects.equals(selectedExpense.get(), other.selectedExpense.get())
                && Objects.equals(selectedDebt.get(), other.selectedDebt.get())
                && Objects.equals(selectedRecurring.get(), other.selectedRecurring.get())
                && Objects.equals(selectedBudget.get(), other.selectedBudget.get());
    }

}
