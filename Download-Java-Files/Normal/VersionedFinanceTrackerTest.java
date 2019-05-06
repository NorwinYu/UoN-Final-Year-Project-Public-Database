package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalDebts.HOLLY;
import static seedu.address.testutil.TypicalDebts.IVAN;
import static seedu.address.testutil.TypicalExpenses.EXPENSE;
import static seedu.address.testutil.TypicalExpenses.GROCERIES;
import static seedu.address.testutil.TypicalExpenses.JAPAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FinanceTrackerBuilder;

public class VersionedFinanceTrackerTest {

    private final ReadOnlyFinanceTracker financeTrackerWithExpense = new FinanceTrackerBuilder().withExpense(
            EXPENSE).build();
    private final ReadOnlyFinanceTracker financeTrackerWithJapan = new FinanceTrackerBuilder().withExpense(
            JAPAN).build();
    private final ReadOnlyFinanceTracker financeTrackerWithGroceries = new FinanceTrackerBuilder().withExpense(
            GROCERIES).build();
    private final ReadOnlyFinanceTracker financeTrackerWithHolly = new FinanceTrackerBuilder().withDebt(
            HOLLY).build();
    private final ReadOnlyFinanceTracker financeTrackerWithIvan = new FinanceTrackerBuilder().withDebt(
            IVAN).build();
    private final ReadOnlyFinanceTracker emptyFinanceTracker = new FinanceTrackerBuilder().build();

    @Test
    public void commit_singleFinanceTracker_noStatesRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(emptyFinanceTracker);

        versionedFinanceTracker.commit();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                emptyFinanceTracker,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFinanceTrackerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly);

        versionedFinanceTracker.commit();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly),
                financeTrackerWithHolly,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFinanceTrackerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        versionedFinanceTracker.commit();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Collections.singletonList(emptyFinanceTracker),
                emptyFinanceTracker,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleFinanceTrackerPointerAtEndOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);

        assertTrue(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_multipleFinanceTrackerPointerNotAtStartOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        assertTrue(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_singleFinanceTracker_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(emptyFinanceTracker);

        assertFalse(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canUndo_multipleFinanceTrackerPointerAtStartOfStateList_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 4);

        assertFalse(versionedFinanceTracker.canUndo());
    }

    @Test
    public void canRedo_multipleFinanceTrackerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        assertTrue(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_multipleFinanceTrackerPointerAtStartOfStateList_returnsTrue() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        assertTrue(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_singleFinanceTracker_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(emptyFinanceTracker);

        assertFalse(versionedFinanceTracker.canRedo());
    }

    @Test
    public void canRedo_multipleFinanceTrackerPointerAtEndOfStateList_returnsFalse() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithJapan);

        assertFalse(versionedFinanceTracker.canRedo());
    }

    @Test
    public void undo_multipleFinanceTrackerPointerAtEndOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);

        versionedFinanceTracker.undo();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly),
                financeTrackerWithJapan,
                Collections.singletonList(financeTrackerWithIvan));
    }

    @Test
    public void undo_multipleFinanceTrackerPointerNotAtStartOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        versionedFinanceTracker.undo();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense),
                financeTrackerWithHolly,
                Arrays.asList(financeTrackerWithJapan, financeTrackerWithIvan));
    }

    @Test
    public void undo_singleFinanceTracker_throwsNoUndoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(emptyFinanceTracker);

        assertThrows(VersionedFinanceTracker.NoUndoableStateException.class, versionedFinanceTracker::undo);
    }

    @Test
    public void undo_multipleFinanceTrackerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 4);

        assertThrows(VersionedFinanceTracker.NoUndoableStateException.class, versionedFinanceTracker::undo);
    }

    @Test
    public void redo_multipleFinanceTrackerPointerNotAtEndOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);

        versionedFinanceTracker.redo();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan), financeTrackerWithIvan, Collections.emptyList());
    }

    @Test
    public void redo_multipleFinanceTrackerPointerAtStartOfStateList_success() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 2);

        versionedFinanceTracker.redo();
        assertFinanceTrackerListStatus(versionedFinanceTracker,
                Arrays.asList(emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly),
                financeTrackerWithJapan, Collections.singletonList(financeTrackerWithIvan));
    }

    @Test
    public void redo_singleFinanceTracker_throwsNoRedoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(emptyFinanceTracker);

        assertThrows(VersionedFinanceTracker.NoRedoableStateException.class, versionedFinanceTracker::redo);
    }

    @Test
    public void redo_multipleFinanceTrackerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(
                emptyFinanceTracker, financeTrackerWithExpense, financeTrackerWithHolly,
                financeTrackerWithJapan, financeTrackerWithIvan);

        assertThrows(VersionedFinanceTracker.NoRedoableStateException.class, versionedFinanceTracker::redo);
    }

    @Test
    public void equals() {
        VersionedFinanceTracker versionedFinanceTracker = prepareFinanceTrackerList(financeTrackerWithExpense,
                financeTrackerWithHolly);

        // same values -> returns true
        VersionedFinanceTracker copy = prepareFinanceTrackerList(financeTrackerWithExpense,
                financeTrackerWithHolly);
        assertTrue(versionedFinanceTracker.equals(copy));

        // same object -> returns true
        assertTrue(versionedFinanceTracker.equals(versionedFinanceTracker));

        // null -> returns false
        assertFalse(versionedFinanceTracker.equals(null));

        // different types -> returns false
        assertFalse(versionedFinanceTracker.equals(1));

        // different state list -> returns false
        VersionedFinanceTracker differentFinanceTrackerList = prepareFinanceTrackerList(
                financeTrackerWithGroceries, financeTrackerWithHolly);
        assertFalse(versionedFinanceTracker.equals(differentFinanceTrackerList));

        // different current pointer index -> returns false
        VersionedFinanceTracker differentCurrentStatePointer = prepareFinanceTrackerList(
                financeTrackerWithExpense, financeTrackerWithHolly);
        shiftCurrentStatePointerLeftwards(versionedFinanceTracker, 1);
        assertFalse(versionedFinanceTracker.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFinanceTracker} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFinanceTracker#currentStatePointer} is equal to
     * {@code expectedStatesBeforePointer},
     * and states after {@code versionedFinanceTracker#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertFinanceTrackerListStatus(VersionedFinanceTracker versionedFinanceTracker,
                                                List<ReadOnlyFinanceTracker> expectedStatesBeforePointer,
                                                ReadOnlyFinanceTracker expectedCurrentState,
                                                List<ReadOnlyFinanceTracker> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FinanceTracker(versionedFinanceTracker), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFinanceTracker.canUndo()) {
            versionedFinanceTracker.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFinanceTracker expectedFinanceTracker : expectedStatesBeforePointer) {
            assertEquals(expectedFinanceTracker, new FinanceTracker(versionedFinanceTracker));
            versionedFinanceTracker.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFinanceTracker expectedFinanceTracker : expectedStatesAfterPointer) {
            versionedFinanceTracker.redo();
            assertEquals(expectedFinanceTracker, new FinanceTracker(versionedFinanceTracker));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFinanceTracker.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFinanceTracker.undo());
    }

    /**
     * Creates and returns a {@code VersionedFinanceTracker} with the {@code financeTrackerStates} added into it,
     * and the {@code VersionedFinanceTracker#currentStatePointer} at the end of list.
     */
    private VersionedFinanceTracker prepareFinanceTrackerList(ReadOnlyFinanceTracker... financeTrackerStates) {
        assertFalse(financeTrackerStates.length == 0);

        VersionedFinanceTracker versionedFinanceTracker = new VersionedFinanceTracker(financeTrackerStates[0]);
        for (int i = 1; i < financeTrackerStates.length; i++) {
            versionedFinanceTracker.resetData(financeTrackerStates[i]);
            versionedFinanceTracker.commit();
        }

        return versionedFinanceTracker;
    }

    /**
     * Shifts the {@code versionedFinanceTracker#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFinanceTracker versionedFinanceTracker, int count) {
        for (int i = 0; i < count; i++) {
            versionedFinanceTracker.undo();
        }
    }
}
