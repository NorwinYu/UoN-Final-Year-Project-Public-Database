package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.DOCTOR;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Expense(null, new Amount("1"), new Date("01-01-2018"),
                        Category.valueOf("FOOD"), null));
        Assert.assertThrows(NullPointerException.class, () ->
                new Expense(new Name("rice"), null, new Date("01-01-2018"),
                        Category.valueOf("FOOD"), null));
        Assert.assertThrows(NullPointerException.class, () ->
                new Expense(new Name("rice"), new Amount("1"), new Date("01-01-2018"),
                        null, null));
    }

    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(DUCK_RICE.isSameExpense(DUCK_RICE));

        // null -> returns false
        assertFalse(DUCK_RICE.isSameExpense(null));

        // different name -> returns false
        Expense editedExpense = new ExpenseBuilder(DUCK_RICE).withName(VALID_NAME_EXPENSE).build();
        assertFalse(DUCK_RICE.isSameExpense(editedExpense));

        // different amount -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withAmount(VALID_AMOUNT_EXPENSE).build();
        assertFalse(DUCK_RICE.isSameExpense(editedExpense));

        // different date -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withDate(VALID_DATE_EXPENSE).build();
        assertFalse(DUCK_RICE.isSameExpense(editedExpense));

        // same name, same amount, same date, different attributes -> returns true
        editedExpense = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_EXPENSE)
                .withRemarks(VALID_REMARKS_EXPENSE).build();
        assertTrue(DUCK_RICE.isSameExpense(editedExpense));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense expenseCopy = new ExpenseBuilder(DUCK_RICE).build();
        assertTrue(DUCK_RICE.equals(expenseCopy));

        // same object -> returns true
        assertTrue(DUCK_RICE.equals(DUCK_RICE));

        // null -> returns false
        assertFalse(DUCK_RICE.equals(null));

        // different type -> returns false
        assertFalse(DUCK_RICE.equals(5));

        // different expense -> returns false
        assertFalse(DUCK_RICE.equals(DOCTOR));

        // different name -> returns false
        Expense editedExpense = new ExpenseBuilder(DUCK_RICE).withName(VALID_NAME_EXPENSE).build();
        assertFalse(DUCK_RICE.equals(editedExpense));

        // different amount -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withAmount(VALID_AMOUNT_EXPENSE).build();
        assertFalse(DUCK_RICE.equals(editedExpense));

        // different date -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withDate(VALID_DATE_EXPENSE).build();
        assertFalse(DUCK_RICE.equals(editedExpense));

        // different category -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_EXPENSE_2).build();
        assertFalse(DUCK_RICE.equals(editedExpense));

        // different remarks -> returns false
        editedExpense = new ExpenseBuilder(DUCK_RICE).withRemarks(VALID_REMARKS_EXPENSE).build();
        assertFalse(DUCK_RICE.equals(editedExpense));
    }
}
