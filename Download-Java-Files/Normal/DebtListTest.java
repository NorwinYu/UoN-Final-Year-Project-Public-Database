package seedu.address.model.debt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalDebts.BOB;
import static seedu.address.testutil.TypicalDebts.FRANK;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.debt.exceptions.DebtNotFoundException;
import seedu.address.testutil.DebtBuilder;

public class DebtListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DebtList debtList = new DebtList();

    @Test
    public void contains_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.contains(null);
    }

    @Test
    public void contains_debtNotInList_returnsFalse() {
        assertFalse(debtList.contains(AMY));
    }

    @Test
    public void contains_debtInList_returnsTrue() {
        debtList.add(AMY);
        assertTrue(debtList.contains(AMY));
    }

    @Test
    public void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        // expenses just need to have same personOwed and amount to be classified as the same
        debtList.add(AMY);
        Debt editedDebt = new DebtBuilder(AMY).withCategory(VALID_CATEGORY_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();
        assertTrue(debtList.contains(editedDebt));
    }

    @Test
    public void contains_debtWithSameToStringInList_returnsTrue() {
        // check if the string format of expense in expense list is same as string format of added
        debtList.add(AMY);
        debtList.add(BOB);
        Iterator<Debt> iter = debtList.iterator();
        assertTrue(iter.next().toString().equals(AMY.toString())
                && iter.next().toString().equals(BOB.toString()));
    }

    @Test
    public void add_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.add(null);
    }

    @Test
    public void setDebt_nullTargetDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebt(null, AMY);
    }

    @Test
    public void setDebt_nullEditedDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebt(AMY, null);
    }

    @Test
    public void setDebt_targetDebtNotInList_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        debtList.setDebt(AMY, AMY);
    }

    @Test
    public void setDebt_editedDebtIsSameDebt_success() {
        debtList.add(AMY);
        debtList.setDebt(AMY, AMY);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(AMY);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebt_editedDebtHasSameIdentity_success() {
        debtList.add(AMY);
        Debt editedDebt = new DebtBuilder(AMY).withCategory(VALID_CATEGORY_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();
        debtList.setDebt(AMY, editedDebt);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(editedDebt);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebt_editedDebtHasDifferentIdentity_success() {
        debtList.add(AMY);
        debtList.setDebt(AMY, FRANK);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(FRANK);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebts_nullDebtList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebts((DebtList) null);
    }

    @Test
    public void setDebts_debtList_replacesOwnListWithProvidedDebtList() {
        debtList.add(AMY);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(FRANK);
        debtList.setDebts(expectedDebtList);
        assertEquals(expectedDebtList, debtList);
    }

    @Test
    public void setDebts_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.setDebts((List<Debt>) null);
    }

    @Test
    public void setDebts_list_replacesOwnListWithProvidedList() {
        debtList.add(AMY);
        List<Debt> debtList = Collections.singletonList(FRANK);
        this.debtList.setDebts(debtList);
        DebtList expectedDebtList = new DebtList();
        expectedDebtList.add(FRANK);
        assertEquals(expectedDebtList, this.debtList);
    }

    @Test
    public void remove_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        debtList.remove(null);
    }

    @Test
    public void remove_debtDoesNotExist_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        debtList.remove(AMY);
    }

    @Test
    public void remove_existingDebt_removesDebt() {
        debtList.add(AMY);
        debtList.remove(AMY);
        DebtList expectedExpenseList = new DebtList();
        assertEquals(expectedExpenseList, debtList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        debtList.asUnmodifiableObservableList().remove(0);
    }
}
