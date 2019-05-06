package seedu.address.model.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURRENCE_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_RECURRING_2;
import static seedu.address.testutil.TypicalRecurrings.DAILY_LUNCH;
import static seedu.address.testutil.TypicalRecurrings.PHONE_BILL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.testutil.Assert;
import seedu.address.testutil.RecurringBuilder;

public class RecurringTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(null, new Amount("1"), new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null, new Frequency("d"), new Occurrence("2")));
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(new Name("rice"), null, new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null, new Frequency("d"), new Occurrence("2")));
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(new Name("rice"), new Amount("1"), null,
                        Category.valueOf("FOOD"), null, new Frequency("d"), new Occurrence("2")));
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(new Name("rice"), new Amount("1"), new Date("01-01-2020"),
                        null, null, new Frequency("d"), new Occurrence("2")));
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(new Name("rice"), new Amount("1"), new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null, null, new Occurrence("2")));
        Assert.assertThrows(NullPointerException.class, () ->
                new Recurring(new Name("rice"), new Amount("1"), new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null, new Frequency("d"), null));
    }

    @Test
    public void isSameRecurring() {
        // same object -> returns true
        assertTrue(PHONE_BILL.isSameRecurring(PHONE_BILL));

        // null -> returns false
        assertFalse(PHONE_BILL.isSameRecurring(null));

        // different name -> returns false
        Recurring editedRecurring = new RecurringBuilder(PHONE_BILL).withName(VALID_NAME_RECURRING_2).build();
        assertFalse(PHONE_BILL.isSameRecurring(editedRecurring));

        // different amount -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withAmount(VALID_AMOUNT_RECURRING_2).build();
        assertFalse(PHONE_BILL.isSameRecurring(editedRecurring));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recurring recurringCopy = new RecurringBuilder(PHONE_BILL).build();
        assertTrue(PHONE_BILL.equals(recurringCopy));

        // same object -> returns true
        assertTrue(PHONE_BILL.equals(PHONE_BILL));

        // null -> returns false
        assertFalse(PHONE_BILL.equals(null));

        // different type -> returns false
        assertFalse(PHONE_BILL.equals(5));

        // different expense -> returns false
        assertFalse(PHONE_BILL.equals(DAILY_LUNCH));

        // different name -> returns false
        Recurring editedRecurring = new RecurringBuilder(PHONE_BILL).withName(VALID_NAME_RECURRING_2).build();
        assertFalse(PHONE_BILL.equals(editedRecurring));

        // different amount -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withAmount(VALID_AMOUNT_RECURRING_2).build();
        assertFalse(PHONE_BILL.equals(editedRecurring));

        // different date -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withDate(VALID_DATE_RECURRING_2).build();
        assertFalse(PHONE_BILL.equals(editedRecurring));

        // different category -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withCategory("food").build();
        assertFalse(PHONE_BILL.equals(editedRecurring));

        // different remarks -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withRemarks(VALID_REMARKS_RECURRING_2).build();
        assertFalse(PHONE_BILL.equals(editedRecurring));


        // different frequency -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withFrequency("Y").build();
        assertFalse(PHONE_BILL.equals(editedRecurring));

        // different occurrence -> returns false
        editedRecurring = new RecurringBuilder(PHONE_BILL).withOccurrence(VALID_OCCURRENCE_RECURRING_2).build();
        assertFalse(PHONE_BILL.equals(editedRecurring));
    }

}
