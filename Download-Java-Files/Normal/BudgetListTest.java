package seedu.address.model.budget;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBudgets.BUDGET;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;
import static seedu.address.testutil.TypicalBudgets.HEALTHCARE_BUDGET;
import static seedu.address.testutil.TypicalBudgets.TRAVEL_BUDGET;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BudgetListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BudgetList budgetList = new BudgetList();

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.contains(null);
    }

    @Test
    public void contains_budgetNotInList_returnsFalse() {
        assertFalse(budgetList.contains(BUDGET));
    }

    @Test
    public void contains_budgetInList_returnsTrue() {
        budgetList.addBudget(FOOD_BUDGET);
        assertTrue(budgetList.contains(FOOD_BUDGET));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.addBudget(null);
    }

    @Test
    public void setBudget_nullTargetBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.setBudget(null, TRAVEL_BUDGET);
    }

    @Test
    public void setBudget_nullEditedBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.setBudget(FOOD_BUDGET, null);
    }

    @Test
    public void setExpense_targetBudgetNotInList_throwsBudgetNotFoundException() {
        thrown.expect(BudgetNotFoundException.class);
        budgetList.setBudget(BUDGET, BUDGET);
    }

    @Test
    public void setBudget_nullBudgetList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.setBudgets((BudgetList) null);
    }

    @Test
    public void setBudgets_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.setBudgets((List<Budget>) null);
    }

    @Test
    public void remove_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        budgetList.removeBudget(null);
    }

    @Test
    public void remove_budgetDoesNotExist_throwsBudgetNotFoundException() {
        thrown.expect(BudgetNotFoundException.class);
        budgetList.removeBudget(HEALTHCARE_BUDGET);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        budgetList.asUnmodifiableObservableList().remove(0);
    }
}
