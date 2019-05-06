package seedu.address.model.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an occurrence in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccurrence(String)}
 */
public class Occurrence {

    public static final String MESSAGE_CONSTRAINTS =
            "Occurrence should be a number from 1 to 999, inclusive.";
    public static final String VALIDATION_REGEX = "^([1-9]|[1-9][0-9]|[1-9][0-9][0-9])$";
    public final int value;

    /**
     * Constructs a {@code Occurrence}.
     *
     * @param occurrence A valid occurrence.
     */
    public Occurrence(String occurrence) {
        requireNonNull(occurrence);
        checkArgument(isValidOccurrence(occurrence), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(occurrence);
    }

    /**
     * Returns true if a given string is a valid occurrence.
     */
    public static boolean isValidOccurrence(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occurrence // instanceof handles nulls
                && value == ((Occurrence) other).value); // state check
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }

}
