package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccurrenceTest {

    @Test
    public void isValidOccurrence() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Occurrence.isValidOccurrence(null));

        // invalid Occurrence
        assertFalse(Occurrence.isValidOccurrence(" ")); // spaces only
        assertFalse(Occurrence.isValidOccurrence("transpot")); // words rather than numbers error
        assertFalse(Occurrence.isValidOccurrence("0")); // one value below boundary
        assertFalse(Occurrence.isValidOccurrence("1000")); // one value above boundary

        // valid Occurrence
        assertTrue(Occurrence.isValidOccurrence("1")); // lower bound of boundary
        assertTrue(Occurrence.isValidOccurrence("999")); // upper bound of boundary
    }
}
