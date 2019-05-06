package seedu.address.model.recurring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_RECURRING;
import static seedu.address.testutil.TypicalRecurrings.DAILY_LUNCH;
import static seedu.address.testutil.TypicalRecurrings.PHONE_BILL;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.recurring.exceptions.RecurringNotFoundException;
import seedu.address.testutil.RecurringBuilder;

public class RecurringListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RecurringList recurringList = new RecurringList();

    @Test
    public void contains_nullRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.contains(null);
    }

    @Test
    public void contains_recurringNotInList_returnsFalse() {
        assertFalse(recurringList.contains(PHONE_BILL));
    }

    @Test
    public void contains_recurringInList_returnsTrue() {
        recurringList.add(PHONE_BILL);
        assertTrue(recurringList.contains(PHONE_BILL));
    }

    @Test
    public void contains_recurringWithSameIdentityFieldsInList_returnsTrue() {
        recurringList.add(PHONE_BILL);
        Recurring editedRecurring = new RecurringBuilder(PHONE_BILL).withCategory(VALID_CATEGORY_RECURRING)
                .withRemarks(VALID_REMARKS_RECURRING).build();
        assertTrue(recurringList.contains(editedRecurring));
    }

    @Test
    public void contains_recurringWithSameToStringInList_returnsTrue() {
        recurringList.add(PHONE_BILL);
        recurringList.add(DAILY_LUNCH);
        Iterator<Recurring> iter = recurringList.iterator();
        assertTrue(iter.next().toString().equals(PHONE_BILL.toString())
                && iter.next().toString().equals(DAILY_LUNCH.toString()));
    }

    @Test
    public void add_nullRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.add(null);
    }

    @Test
    public void setRecurring_nullTargetRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.setRecurring(null, PHONE_BILL);
    }

    @Test
    public void setRecurring_nullEditedRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.setRecurring(PHONE_BILL, null);
    }

    @Test
    public void setRecurring_targetRecurringNotInList_throwsRecurringNotFoundException() {
        thrown.expect(RecurringNotFoundException.class);
        recurringList.setRecurring(PHONE_BILL, PHONE_BILL);
    }

    @Test
    public void setRecurring_editedRecurringIsSameRecurring_success() {
        recurringList.add(PHONE_BILL);
        recurringList.setRecurring(PHONE_BILL, PHONE_BILL);
        RecurringList expectedRecurringList = new RecurringList();
        expectedRecurringList.add(PHONE_BILL);
        assertEquals(expectedRecurringList, recurringList);
    }

    @Test
    public void setRecurring_editedRecurringHasSameIdentity_success() {
        recurringList.add(PHONE_BILL);
        Recurring editedRecurring = new RecurringBuilder(PHONE_BILL).withCategory(VALID_CATEGORY_RECURRING)
                .withRemarks(VALID_REMARKS_RECURRING).build();
        recurringList.setRecurring(PHONE_BILL, editedRecurring);
        RecurringList expectedRecurringList = new RecurringList();
        expectedRecurringList.add(editedRecurring);
        assertEquals(expectedRecurringList, recurringList);
    }

    @Test
    public void setRecurring_editedRecurringeHasDifferentIdentity_success() {
        recurringList.add(PHONE_BILL);
        recurringList.setRecurring(PHONE_BILL, DAILY_LUNCH);
        RecurringList expectedRecurringList = new RecurringList();
        expectedRecurringList.add(DAILY_LUNCH);
        assertEquals(expectedRecurringList, recurringList);
    }

    @Test
    public void setRecurring_nullRecurringList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.setRecurring((RecurringList) null);
    }

    @Test
    public void setRecurring_recurringList_replacesOwnListWithProvidedRecurringList() {
        recurringList.add(PHONE_BILL);
        RecurringList expectedRecurringList = new RecurringList();
        expectedRecurringList.add(DAILY_LUNCH);
        recurringList.setRecurring(expectedRecurringList);
        assertEquals(expectedRecurringList, recurringList);
    }

    @Test
    public void setRecurring_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.setRecurring((List<Recurring>) null);
    }

    @Test
    public void setRecurring_list_replacesOwnListWithProvidedList() {
        recurringList.add(PHONE_BILL);
        List<Recurring> recurringList = Collections.singletonList(DAILY_LUNCH);
        this.recurringList.setRecurring(recurringList);
        RecurringList expectedRecurringList = new RecurringList();
        expectedRecurringList.add(DAILY_LUNCH);
        assertEquals(expectedRecurringList, this.recurringList);
    }

    @Test
    public void remove_nullRecurring_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recurringList.remove(null);
    }

    @Test
    public void remove_recurringDoesNotExist_throwsRecurringNotFoundException() {
        thrown.expect(RecurringNotFoundException.class);
        recurringList.remove(PHONE_BILL);
    }

    @Test
    public void remove_existingRecurring_removesRecurring() {
        recurringList.add(PHONE_BILL);
        recurringList.remove(PHONE_BILL);
        RecurringList expectedRecurringList = new RecurringList();
        assertEquals(expectedRecurringList, recurringList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        recurringList.asUnmodifiableObservableList().remove(0);
    }
}
