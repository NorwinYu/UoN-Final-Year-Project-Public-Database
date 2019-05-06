package seedu.address.model.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructorInvalidAmount_emptyString_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount numbers
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("amount")); // non-numeric
        assertFalse(Amount.isValidAmount("0")); // 0 value
        assertFalse(Amount.isValidAmount("-1")); // negative value
        assertFalse(Amount.isValidAmount("91p041")); // alphabets within digits
        assertFalse(Amount.isValidAmount("12345678")); // more than 7 digits in dollars
        assertFalse(Amount.isValidAmount("1.")); // . without decimal places
        assertFalse(Amount.isValidAmount("1.234")); // 3 decimal places, max 2
        assertFalse(Amount.isValidAmount("0")); // 0 dollars
        assertFalse(Amount.isValidAmount("00000001.23")); // more than 7 digits before decimal point

        // valid amount numbers
        assertTrue(Amount.isValidAmount("1")); // exactly 1 number
        assertTrue(Amount.isValidAmount("1234567")); // 7 digit numbers
        assertTrue(Amount.isValidAmount("0000001")); // 1 digit with 6 zeros padding
        assertTrue(Amount.isValidAmount("1234567.25")); // 7 digit numbers with 2 decimal places
        assertTrue(Amount.isValidAmount("1.2")); // 1 decimal place
        assertTrue(Amount.isValidAmount("1.23")); // 2 decimal places
    }
}
