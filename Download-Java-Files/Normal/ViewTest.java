package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ViewTest {

    @Test
    public void isValidView() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> View.isValidView(null));

        // invalid View
        assertFalse(View.isValidView("")); // empty string
        assertFalse(View.isValidView(" ")); // spaces only
        assertFalse(View.isValidView("transpot")); // spelling error
        assertFalse(View.isValidView("91p041")); // invalid category
        assertFalse(View.isValidView("1000")); // invalid category with missing $

        // valid View with varying alphabetical cases
        assertTrue(View.isValidView("food")); // in lower case
        assertTrue(View.isValidView("SHOPPING")); // in upper case
        assertTrue(View.isValidView("HeAlThCaRe")); // mixed case

        // all other valid views
        assertTrue(View.isValidView("transPort"));
        assertTrue(View.isValidView("UTILITIES"));
        assertTrue(View.isValidView("entertainment"));
        assertTrue(View.isValidView("work"));
        assertTrue(View.isValidView("SHopping"));
        assertTrue(View.isValidView("others"));
        assertTrue(View.isValidView("day"));
        assertTrue(View.isValidView("Week"));
        assertTrue(View.isValidView("YeaR"));
        assertTrue(View.isValidView("month"));
        assertTrue(View.isValidView("$100"));
        assertTrue(View.isValidView("$1000"));
        assertTrue(View.isValidView("$10"));
    }
}
