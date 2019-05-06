package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CategoryTest {

    @Test
    public void isValidCategory() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Category.isValid(null));

        // invalid Categories
        assertFalse(Category.isValid("")); // empty string
        assertFalse(Category.isValid(" ")); // spaces only
        assertFalse(Category.isValid("transpot")); // spelling error
        assertFalse(Category.isValid("91p041")); // invalid category

        // valid Categories
        assertTrue(Category.isValid("food")); // in lower case
        assertTrue(Category.isValid("SHOPPING")); // in upper case
        assertTrue(Category.isValid("HeAlThCaRe")); // mixed case
        assertTrue(Category.isValid("TRAvel")); // mixed case
    }
}
