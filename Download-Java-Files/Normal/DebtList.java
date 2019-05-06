package seedu.address.model.debt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.debt.exceptions.DebtNotFoundException;

/**
 * A list of debts that does not allow nulls. Duplicates are allowed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Debt
 */
public class DebtList implements Iterable<Debt> {

    private final ObservableList<Debt> internalList = FXCollections.observableArrayList();
    private final ObservableList<Debt> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent debt as the given argument.
     */
    public boolean contains(Debt toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDebt);
    }

    /**
     * Adds a debt to the list.
     */
    public void add(Debt toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the debt {@code target} in the list with {@code editedDebt}.
     * {@code target} must exist in the list.
     */
    public void setDebt(Debt target, Debt editedDebt) {
        requireAllNonNull(target, editedDebt);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DebtNotFoundException();
        }

        internalList.set(index, editedDebt);
    }

    /**
     * Replaces entire debt list {@code internalList} with new debt list {@code replacement}.
     */
    public void setDebts(DebtList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code expenses}.
     */
    public void setDebts(List<Debt> debts) {
        requireAllNonNull(debts);
        internalList.setAll(debts);
    }

    /**
     * Removes the equivalent debt from the list.
     * The debt must exist in the list.
     */
    public void remove(Debt toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DebtNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Debt> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Debt> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DebtList // instanceof handles nulls
                && internalList.equals(((DebtList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
