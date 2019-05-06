package seedu.address.model.debt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalDebts.FRANK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.testutil.Assert;
import seedu.address.testutil.DebtBuilder;

public class DebtTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Debt(null, new Amount("1"), new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null));
        Assert.assertThrows(NullPointerException.class, () ->
                new Debt(new Name("rice"), null, new Date("01-01-2020"),
                        Category.valueOf("FOOD"), null));
        Assert.assertThrows(NullPointerException.class, () ->
                new Debt(new Name("rice"), new Amount("1"), null,
                        Category.valueOf("FOOD"), null));
        Assert.assertThrows(NullPointerException.class, () ->
                new Debt(new Name("rice"), new Amount("1"), new Date("01-01-2020"),
                        null, null));
    }

    @Test
    public void isSameDebt() {
        // same object -> returns true
        assertTrue(AMY.isSameDebt(AMY));

        // null -> returns false
        assertFalse(AMY.isSameDebt(null));

        // different name -> returns false
        Debt editedDebt = new DebtBuilder(AMY).withPersonOwed(VALID_NAME_DEBT).build();
        assertFalse(AMY.isSameDebt(editedDebt));

        // different amount -> returns false
        editedDebt = new DebtBuilder(AMY).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(AMY.isSameDebt(editedDebt));

        // same name, same amount, different attributes -> returns true
        editedDebt = new DebtBuilder(AMY).withDeadline(VALID_DEADLINE_DEBT)
                .withCategory(VALID_CATEGORY_DEBT).withRemarks(VALID_REMARKS_DEBT).build();
        assertTrue(AMY.isSameDebt(editedDebt));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Debt debtCopy = new DebtBuilder(AMY).build();
        assertTrue(AMY.equals(debtCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different debt -> returns false
        assertFalse(AMY.equals(FRANK));

        // different person owed -> returns false
        Debt editedDebt = new DebtBuilder(AMY).withPersonOwed(VALID_NAME_DEBT).build();
        assertFalse(AMY.equals(editedDebt));

        // different amount -> returns false
        editedDebt = new DebtBuilder(AMY).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(AMY.equals(editedDebt));

        // different deadline -> returns false
        editedDebt = new DebtBuilder(AMY).withDeadline(VALID_DEADLINE_DEBT).build();
        assertFalse(AMY.equals(editedDebt));

        // different category -> returns false
        editedDebt = new DebtBuilder(AMY).withCategory(VALID_CATEGORY_DEBT).build();
        assertFalse(AMY.equals(editedDebt));

        // different remarks -> returns false
        editedDebt = new DebtBuilder(AMY).withRemarks(VALID_REMARKS_DEBT).build();
        assertFalse(AMY.equals(editedDebt));
    }
}
