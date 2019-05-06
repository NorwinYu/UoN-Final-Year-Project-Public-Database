package seedu.address.model.attributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an frequency in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidFrequency(String)}
 */
public class Frequency {

    public static final String MESSAGE_CONSTRAINTS =
            "Frequency should only contain an alphabet 'D', 'W', 'M' or 'Y' for daily, weekly, monthly or yearly "
                    + "respectively. Any numbers following the alphabet means a multiple of that frequency e.g. "
                    + "W2 means fortnightly (every 2 weeks).";
    public static final String VALIDATION_REGEX = "[DWMYdwmy]\\d*";
    public final String value;

    /**
     * Constructs a {@code Frequency}.
     *
     * @param frequency A valid frequency.
     */
    public Frequency(String frequency) {
        requireNonNull(frequency);
        checkArgument(isValidFrequency(frequency), MESSAGE_CONSTRAINTS);
        value = frequency;
    }

    /**
     * Returns true if a given string is a valid frequency.
     */
    public static boolean isValidFrequency(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Frequency // instanceof handles nulls
                && value.equals(((Frequency) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
