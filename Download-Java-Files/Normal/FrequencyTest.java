package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FrequencyTest {

    @Test
    public void isValidFrequency() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Frequency.isValidFrequency(null));

        // invalid Occurrence
        assertFalse(Frequency.isValidFrequency(" ")); // spaces only
        assertFalse(Frequency.isValidFrequency("a")); // invalid arg

        // valid Occurrence
        assertTrue(Frequency.isValidFrequency("m")); // lower case input
        assertTrue(Frequency.isValidFrequency("M")); // upper case input
    }
}
