package seedu.address.model.expense;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.DOCTOR;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.TAXI;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ExpenseList expenseList = new ExpenseList();

    @Test
    public void contains_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.contains(null);
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(expenseList.contains(DUCK_RICE));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        expenseList.add(DUCK_RICE);
        assertTrue(expenseList.contains(DUCK_RICE));
    }

    @Test
    public void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        // expenses just need to have same name, amount and date to be classified as the same
        expenseList.add(DUCK_RICE);
        Expense editedExpense = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_EXPENSE)
                .withRemarks(VALID_REMARKS_EXPENSE).build();
        assertTrue(expenseList.contains(editedExpense));
    }

    @Test
    public void contains_expenseWithSameToStringInList_returnsTrue() {
        // check if the string format of expense in expense list is same as string format of added
        expenseList.add(DUCK_RICE);
        expenseList.add(TAXI);
        Iterator<Expense> iter = expenseList.iterator();
        assertTrue(iter.next().toString().equals(TAXI.toString())
                && iter.next().toString().equals(DUCK_RICE.toString()));
    }

    @Test
    public void add_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.add(null);
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.setExpense(null, DUCK_RICE);
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.setExpense(DUCK_RICE, null);
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        expenseList.setExpense(DUCK_RICE, DUCK_RICE);
    }

    @Test
    public void setExpense_editedExpenseIsSameExpense_success() {
        expenseList.add(DUCK_RICE);
        expenseList.setExpense(DUCK_RICE, DUCK_RICE);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(DUCK_RICE);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpense_editedExpenseHasSameIdentity_success() {
        expenseList.add(DUCK_RICE);
        Expense editedExpense = new ExpenseBuilder(DUCK_RICE).withCategory(VALID_CATEGORY_EXPENSE)
                .withRemarks(VALID_REMARKS_EXPENSE).build();
        expenseList.setExpense(DUCK_RICE, editedExpense);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(editedExpense);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpense_editedExpenseHasDifferentIdentity_success() {
        expenseList.add(DUCK_RICE);
        expenseList.setExpense(DUCK_RICE, DOCTOR);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(DOCTOR);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpenses_nullExpenseList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.setExpenses((ExpenseList) null);
    }

    @Test
    public void setExpenses_expenseList_replacesOwnListWithProvidedExpenseList() {
        expenseList.add(DUCK_RICE);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(DOCTOR);
        expenseList.setExpenses(expectedExpenseList);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.setExpenses((List<Expense>) null);
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        expenseList.add(DUCK_RICE);
        List<Expense> expenseList = Collections.singletonList(DOCTOR);
        this.expenseList.setExpenses(expenseList);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(DOCTOR);
        assertEquals(expectedExpenseList, this.expenseList);
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        expenseList.remove(null);
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        expenseList.remove(DUCK_RICE);
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        expenseList.add(DUCK_RICE);
        expenseList.remove(DUCK_RICE);
        ExpenseList expectedExpenseList = new ExpenseList();
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        expenseList.asUnmodifiableObservableList().remove(0);
    }
}
