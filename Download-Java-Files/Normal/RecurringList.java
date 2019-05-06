package seedu.address.model.recurring;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recurring.exceptions.DuplicateRecurringException;
import seedu.address.model.recurring.exceptions.RecurringNotFoundException;

/**
 * A list of recurring expenses that does not allow nulls. Duplicates are not allowed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Recurring
 */
public class RecurringList {
    private final ObservableList<Recurring> internalList = FXCollections.observableArrayList();
    private final ObservableList<Recurring> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent recurring as the given argument.
     */
    public boolean contains(Recurring toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecurring);
    }

    /**
     * Adds a recurring to the list.
     * The recurring must not already exist in the list.
     */
    public void add(Recurring toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecurringException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the recurring {@code target} in the list with {@code editedRecurring}.
     * {@code target} must exist in the list.
     * The recurring identity of {@code editedRecurring} must not be the same as another existing recurring in the list.
     */
    public void setRecurring(Recurring target, Recurring editedRecurring) {
        requireAllNonNull(target, editedRecurring);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecurringNotFoundException();
        }

        internalList.set(index, editedRecurring);
    }

    public void setRecurring(RecurringList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code recurrings}.
     * {@code recurrings} may contain duplicate recurrings.
     */
    public void setRecurring(List<Recurring> recurrings) {
        requireAllNonNull(recurrings);
        internalList.setAll(recurrings);
    }

    /**
     * Removes the equivalent recurring from the list.
     * The recurring must exist in the list.
     */
    public void remove(Recurring toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RecurringNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Recurring> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public Iterator<Recurring> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurringList // instanceof handles nulls
                && internalList.equals(((RecurringList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
