package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalExpenses.TAXI;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.testutil.Assert;

public class JsonAdaptedExpenseTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_AMOUNT = "+651a234";
    private static final String INVALID_DATE = "example-date";
    private static final String INVALID_CATEGORY = " ";

    private static final String VALID_NAME = TAXI.getName().toString();
    private static final String VALID_AMOUNT = TAXI.getAmount().toString();
    private static final String VALID_DATE = TAXI.getDate().toString();
    private static final String VALID_CATEGORY = TAXI.getCategory().toString();
    private static final String VALID_REMARKS = TAXI.getRemarks();

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(TAXI);
        assertEquals(TAXI, expense.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(INVALID_NAME, VALID_AMOUNT, VALID_DATE, VALID_CATEGORY, VALID_REMARKS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(null, VALID_AMOUNT, VALID_DATE, VALID_CATEGORY,
                VALID_REMARKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, INVALID_AMOUNT, VALID_DATE, VALID_CATEGORY, VALID_REMARKS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, null, VALID_DATE, VALID_CATEGORY,
                VALID_REMARKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, INVALID_DATE, VALID_CATEGORY, VALID_REMARKS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, VALID_DATE, INVALID_CATEGORY, VALID_REMARKS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, VALID_DATE, null,
                VALID_REMARKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }
}
