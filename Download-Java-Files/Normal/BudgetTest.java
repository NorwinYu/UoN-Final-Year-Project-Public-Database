package seedu.address.model.budget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_BUDGET;
import static seedu.address.testutil.TypicalBudgets.BUDGET;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;
import static seedu.address.testutil.TypicalBudgets.TRAVEL_BUDGET;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.testutil.Assert;
import seedu.address.testutil.BudgetBuilder;

public class BudgetTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Budget(null, new Amount("1"), new Date("01-01-2019"), new Date("31-12-2019"), null, 0, 0));
        Assert.assertThrows(NullPointerException.class, () ->
                new Budget(Category.valueOf("FOOD"), null, new Date("01-01-2019"), new Date("31-12-2019"),
                        null, 0, 0));
        Assert.assertThrows(NullPointerException.class, () ->
                new Budget(Category.valueOf("FOOD"), new Amount("1"), new Date("01-01-2019"), null,
                        null, 0, 0));
    }

    @Test
    public void isSameBudget() {
        // same object -> returns true
        assertTrue(BUDGET.equals(BUDGET));

        // null -> returns false
        assertFalse(BUDGET.equals(null));

        // different category -> returns false
        Budget editedBudget = new BudgetBuilder(FOOD_BUDGET).withCategory(VALID_CATEGORY_DEBT).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different amount -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withAmount(VALID_AMOUNT_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different start date -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withStartDate(VALID_STARTDATE_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different end date -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withEndDate(VALID_ENDDATE_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different remarks -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withRemarks(VALID_REMARKS_DEBT).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Budget budgetCopy = new BudgetBuilder(FOOD_BUDGET).build();
        assertTrue(FOOD_BUDGET.equals(budgetCopy));

        // same object -> returns true
        assertTrue(FOOD_BUDGET.equals(FOOD_BUDGET));

        // null -> returns false
        assertFalse(FOOD_BUDGET.equals(null));

        // different type -> returns false
        assertFalse(FOOD_BUDGET.equals(5));

        // different budget -> returns false
        assertFalse(FOOD_BUDGET.equals(TRAVEL_BUDGET));

        // different category -> returns false
        Budget editedBudget = new BudgetBuilder(FOOD_BUDGET).withCategory(VALID_CATEGORY_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different amount -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withAmount(VALID_AMOUNT_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different start date -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withStartDate(VALID_STARTDATE_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different end date -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withEndDate(VALID_ENDDATE_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));

        // different remarks -> returns false
        editedBudget = new BudgetBuilder(FOOD_BUDGET).withRemarks(VALID_REMARKS_BUDGET).build();
        assertFalse(FOOD_BUDGET.equals(editedBudget));
    }
}
