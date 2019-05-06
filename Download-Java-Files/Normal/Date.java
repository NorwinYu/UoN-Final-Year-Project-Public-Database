package seedu.address.model.attributes;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a localDate in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should only be dd-mm-yyyy format and year must be in between 2000 and 2099.";
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline must not be a date that has already passed.";
    public static final String MESSAGE_DATE_DOES_NOT_EXIST = "Date does not exist.";
    private static final String VALIDATION_REGEX = "\\d{2}-\\d{2}-(20)\\d{2}";
    private LocalDate localDate;

    /**
     * Constructs a {@code Date}.
     * @param date A valid localDate number.
     */
    public Date(String date) throws DateTimeParseException {
        requireNonNull(date);
        this.localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    /**
     * Constructs a {@code Date}.
     * @param date An existing Date.
     */
    public Date(Date date) {
        requireNonNull(date);
        this.localDate = date.localDate;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = LocalDate.parse(localDate, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setLocalDate(Date date) {
        this.localDate = date.getLocalDate();
    }

    /**
     * Returns string that informs if a given string is valid or of the wrong localDate format or does not exist.
     */
    public static String isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return "format";
        } else if (!doesDateExist(test)) {
            return "does not exist";
        }
        return "valid";
    }

    /**
     * Returns true if localDate exists.
     */
    private static boolean doesDateExist(String test) throws DateTimeParseException {
        try {
            LocalDate date = LocalDate.parse(test, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%4d", localDate.getDayOfMonth(), localDate.getMonthValue(),
                localDate.getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && localDate.getDayOfMonth() == (((Date) other).localDate.getDayOfMonth()) // state check
                && localDate.getMonthValue() == (((Date) other).localDate.getMonthValue())
                && localDate.getYear() == ((Date) other).localDate.getYear());
    }

    /**
     * Returns -1, 0 and 1 respectively if Date is earlier than, same as, or later than {@param other}
     */
    @Override
    public int compareTo(Date other) {
        if (this.localDate.isBefore(other.localDate)) {
            return -1;
        }
        if (this.localDate.isAfter(other.localDate)) {
            return 1;
        }
        return 0;
    }

    /**
     * Checks against operating system's localDate and check whether {@code localDate} is equal or after this localDate.
     *
     * @return true if localDate is the same as the system's current localDate or if it falls on a localDate after the
     * system's current localDate, else it returns false
     */
    public boolean isEqualOrAfterToday() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Date currentDate = new Date(dtf.format(LocalDate.now()));

        if (compareTo(currentDate) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param duration is the duration before current date
     * @return true if localDate is within the duration
     */
    public boolean isWithinDurationBeforeToday(String duration) {
        // get today's date
        LocalDate currentDate = LocalDate.now();

        // auto return true if same date
        if (currentDate.equals(localDate)) {
            return true;
        }

        switch (duration) {
        case "day":
            return currentDate.equals(localDate);
        case "week":
            // get date is one week before today's date
            LocalDate lastWeekDate = currentDate.minusWeeks(1);
            return (localDate.isAfter(lastWeekDate) && localDate.isBefore(currentDate));
        case "month":
            // get date is one month before today's date
            LocalDate lastMonthDate = currentDate.minusMonths(1);
            return (localDate.isAfter(lastMonthDate) && localDate.isBefore(currentDate));
        case "year":
            // get date is one year before today's date
            LocalDate lastYearDate = currentDate.minusYears(1);
            return (localDate.isAfter(lastYearDate) && localDate.isBefore(currentDate));
        default:
            return false;
        }
    }

    /**
     *
     * @param duration is the duration after current date
     * @return true if localDate is within the duration
     */
    public boolean isWithinDurationAfterToday(String duration) {
        // get today's date
        LocalDate currentDate = LocalDate.now();
        // auto return true if same date
        if (currentDate.equals(localDate)) {
            return true;
        }
        switch (duration) {
        case "day":
            return currentDate.equals(localDate);
        case "week":
            // get date is one week after today's date
            LocalDate nextWeekDate = currentDate.plusWeeks(1);
            return (localDate.isBefore(nextWeekDate) && localDate.isAfter(currentDate));
        case "month":
            // get date is one month after today's date
            LocalDate nextMonthDate = currentDate.plusMonths(1);
            return (localDate.isBefore(nextMonthDate) && localDate.isAfter(currentDate));
        case "year":
            // get date is one year after today's date
            LocalDate lastYearDate = currentDate.plusYears(1);
            return (localDate.isBefore(lastYearDate) && localDate.isAfter(currentDate));
        default:
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
