package seedu.address.model.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a date in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only be dd-mm-yyyy format.";
    public static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    public final int day;
    public final int month;
    public final int year;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date number.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        int[] datesInt = parseDate(date);
        day = datesInt[0];
        month = datesInt[1];
        year = datesInt[2];
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     */
    private static int[] parseDate(String date) {
        int[] datesInt = new int[3];
        String[] datesString = date.split("-");
        for (int i = 0; i < 3; i++) {
            datesInt[i] = Integer.parseInt(datesString[i]);
        }
        return datesInt;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%4d", day, month, year);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && day == (((Date) other).day) // state check
                && month == (((Date) other).month)
                && year == ((Date) other).year);
    }

    /**
     * Returns -1, 0 and 1 respectively if Date is earlier than, same as, or later than {@param other}
     */
    @Override
    public int compareTo(Date other) {
        if (this.year < other.year) {
            return -1;
        }
        if (this.year > other.year) {
            return 1;
        }

        // years are the same
        if (this.month < other.month) {
            return -1;
        }
        if (this.month > other.month) {
            return 1;
        }

        // years and months are the same
        if (this.day < other.day) {
            return -1;
        }
        if (this.day > other.day) {
            return 1;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
