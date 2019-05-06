package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code FinanceTracker} that keeps track of its own history.
 */
public class VersionedFinanceTracker extends FinanceTracker {

    private final List<ReadOnlyFinanceTracker> financeTrackerStateList;
    private int currentStatePointer;

    public VersionedFinanceTracker(ReadOnlyFinanceTracker initialState) {
        super(initialState);

        financeTrackerStateList = new ArrayList<>();
        financeTrackerStateList.add(new FinanceTracker(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FinanceTracker} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        financeTrackerStateList.add(new FinanceTracker(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        financeTrackerStateList.subList(currentStatePointer + 1, financeTrackerStateList.size()).clear();
    }

    /**
     * Restores the finance tracker to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(financeTrackerStateList.get(currentStatePointer));
    }

    /**
     * Restores the finance tracker to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(financeTrackerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has finance tracker states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has finance tracker states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < financeTrackerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFinanceTracker)) {
            return false;
        }

        VersionedFinanceTracker otherVersionedFinanceTracker = (VersionedFinanceTracker) other;

        // state check
        return super.equals(otherVersionedFinanceTracker)
                && financeTrackerStateList.equals(otherVersionedFinanceTracker.financeTrackerStateList)
                && currentStatePointer == otherVersionedFinanceTracker.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of financeTrackerState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of financeTrackerState list, unable to redo.");
        }
    }
}
