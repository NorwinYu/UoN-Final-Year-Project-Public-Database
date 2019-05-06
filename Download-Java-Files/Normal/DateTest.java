package seedu.address.model.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    public static final String VALID_DATE_1 = "01-01-2000";
    public static final String VALID_DATE_2 = "31-01-2000";
    public static final String VALID_DATE_3 = "01-12-2000";
    public static final String VALID_DATE_4 = "01-01-2015";
    public static final String VALID_DATE_5 = "03-03-2019";
    public static final String INVALID_DATE_1 = "29-02-2019";
    public static final String INVALID_DATE_2 = "32-05-2021";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertSame("format", Date.isValidDate("")); // empty string
        assertSame("format", Date.isValidDate(" ")); // spaces only
        assertSame("format", Date.isValidDate("01-01")); // must be dd-mm-yyyy
        assertSame("format", Date.isValidDate("01-01-18")); // need 4 numbers for year, e.g. 2018
        assertSame("format", Date.isValidDate("01-01-2018a")); // alphabets not allowed
        assertSame("format", Date.isValidDate("date")); // non-numeric
        assertSame("does not exist", Date.isValidDate("29-02-2019")); // invalid day
        assertSame("does not exist", Date.isValidDate("29-13-2019")); // invalid month

        // valid dates
        assertSame("valid", Date.isValidDate("01-01-2019"));
        assertSame("valid", Date.isValidDate("31-12-2009"));
    }

    @Test
    public void isWithinDuration() {
        Date notWithinAnyDurationDate1 = new Date(VALID_DATE_1);
        Date withinMonthDate1 = new Date(VALID_DATE_5);

        // not within the same year -> returns false
        assertFalse(notWithinAnyDurationDate1.isWithinDurationBeforeToday("year"));

        // not within the same day -> returns false
        assertFalse(withinMonthDate1.isWithinDurationBeforeToday("day"));

        // not within the same week -> returns false
        assertFalse(withinMonthDate1.isWithinDurationBeforeToday("week"));

        // within the same year -> return true
        assertTrue(withinMonthDate1.isWithinDurationBeforeToday("year"));
    }

    @Test
    public void equals() {
        Date date1 = new Date(VALID_DATE_1);
        Date date2 = new Date(VALID_DATE_2);
        Date date3 = new Date(VALID_DATE_3);
        Date date4 = new Date(VALID_DATE_4);

        // same date instance -> returns true
        assertTrue(date1.equals(date1));

        // same values -> returns true
        Date date1Copy = new Date(VALID_DATE_1);
        assertTrue(date1.equals(date1Copy));

        // null -> returns false
        assertFalse(date1.equals(null));

        // different type -> returns false
        assertFalse(date1.equals(5));

        // different day -> returns false
        assertFalse(date1.equals(date2));

        // different month -> returns false
        assertFalse(date1.equals(date3));

        // different year -> returns false
        assertFalse(date1.equals(date4));
    }

    @Test
    public void compareTo() {
        // initialize dates in chronological order
        Date date1 = new Date(VALID_DATE_1);
        Date date2 = new Date(VALID_DATE_2);
        Date date3 = new Date(VALID_DATE_3);
        Date date4 = new Date(VALID_DATE_4);

        assertEquals(date1.compareTo(date1), 0);
        assertEquals(date1.compareTo(date2), -1);
        assertEquals(date1.compareTo(date3), -1);
        assertEquals(date1.compareTo(date4), -1);

        assertEquals(date2.compareTo(date1), 1);
        assertEquals(date2.compareTo(date2), 0);
        assertEquals(date2.compareTo(date3), -1);
        assertEquals(date2.compareTo(date4), -1);

        assertEquals(date3.compareTo(date1), 1);
        assertEquals(date3.compareTo(date2), 1);
        assertEquals(date3.compareTo(date3), 0);
        assertEquals(date3.compareTo(date4), -1);

        assertEquals(date4.compareTo(date1), 1);
        assertEquals(date4.compareTo(date2), 1);
        assertEquals(date4.compareTo(date3), 1);
        assertEquals(date4.compareTo(date4), 0);
    }
}
