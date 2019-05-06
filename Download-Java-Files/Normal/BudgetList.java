package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attributes.Category;

/**
 * A list of budgets that does not allow nulls. One for each of the eight category and one for total.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Budget
 */
public class BudgetList implements Iterable<Budget> {

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if list contains a budget that overlaps with given argument
     */
    public boolean hasOverlap(Budget toCheck) {
        requireNonNull(toCheck);
        for (Budget budget : internalList) {
            if (toCheck.overlaps(budget)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the list contains an equivalent budget as the given argument
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public int getIndex(Category category) {
        for (Budget budget : internalList) {
            if (budget.getCategory() == category) {
                return internalList.indexOf(budget);
            }
        }
        return -1;
    }

    /**
     * Adds a budget to the list.
     * Budget cannot be overlapping with another existing budget.
     */
    public void addBudget(Budget toAdd) {
        requireNonNull(toAdd);
        if (hasOverlap(toAdd)) {
            throw new OverlappingBudgetException();
        }
        if (internalList.contains(toAdd)) {
            throw new BudgetExistsException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the budget {@code target} in the list with {@code editedBudget}.
     * {@code target} must exist in the list.
     */
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BudgetNotFoundException();
        }
        internalList.set(index, editedBudget);
    }

    /**
     * Replaces entire budget list {@code internalList} with new budget list {@code replacement}
     */
    public void setBudgets(BudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code budgets}
     */
    public void setBudgets(List<Budget> budgets) {
        requireAllNonNull(budgets);
        internalList.setAll(budgets);
    }

    /**
     * Removes the equivalent budget from the list.
     * The budget must exist in the list.
     */
    public void removeBudget(Budget toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BudgetNotFoundException();
        }
    }

    /**
     * Returns budget at specified category.
     * Does not take care of the case where budget does no exist
     */
    public Budget get(Category category) {
        return internalList.get(getIndex(category));
    }

    /**
     * Returns backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }
}
