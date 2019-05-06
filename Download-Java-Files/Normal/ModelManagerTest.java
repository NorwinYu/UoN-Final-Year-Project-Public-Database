package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalDebts.BOB;
import static seedu.address.testutil.TypicalExpenses.DOCTOR;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.TAXI;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.debt.Debt;
import seedu.address.model.debt.exceptions.DebtNotFoundException;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.NameContainsKeywordsPredicateForExpense;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.DebtBuilder;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.FinanceTrackerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FinanceTracker(), new FinanceTracker(modelManager.getFinanceTracker()));
        assertEquals(null, modelManager.getSelectedExpense());
    }

    //==== UserPrefs-related tests ===========================================================================

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFinanceTrackerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFinanceTrackerFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    //==== Gui-related tests ===========================================================================

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    //==== FinanceTracker-related tests ======================================================================

    @Test
    public void setFinanceTrackerFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setFinanceTrackerFilePath(null);
    }

    @Test
    public void setFinanceTrackerFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setFinanceTrackerFilePath(path);
        assertEquals(path, modelManager.getFinanceTrackerFilePath());
    }

    //==== Expense-related tests ===========================================================================

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasExpense(null);
    }

    @Test
    public void hasExpense_expenseNotInFinanceTracker_returnsFalse() {
        assertFalse(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void hasExpense_expenseInFinanceTracker_returnsTrue() {
        modelManager.addExpense(DUCK_RICE);
        assertTrue(modelManager.hasExpense(DUCK_RICE));
    }

    @Test
    public void deleteExpense_expenseIsSelectedAndFirstExpenseInFilteredExpenseList_selectionCleared() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.setSelectedExpense(DUCK_RICE);
        modelManager.deleteExpense(DUCK_RICE);
        assertEquals(null, modelManager.getSelectedExpense());
    }

    @Test
    public void deleteExpense_expenseIsSelectedAndSecondExpenseInFilteredExpenseList_firstExpenseSelected() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.addExpense(DOCTOR);
        assertEquals(Arrays.asList(DOCTOR, DUCK_RICE), modelManager.getFilteredExpenseList());
        modelManager.setSelectedExpense(DUCK_RICE);
        modelManager.deleteExpense(DUCK_RICE);
        assertEquals(DOCTOR, modelManager.getSelectedExpense());
    }

    @Test
    public void setExpense_expenseIsSelected_selectedExpenseUpdated() {
        modelManager.addExpense(DUCK_RICE);
        modelManager.setSelectedExpense(DUCK_RICE);
        Expense updatedExpense = new ExpenseBuilder(DUCK_RICE).withDate(VALID_DATE_EXPENSE).build();
        modelManager.setExpense(DUCK_RICE, updatedExpense);
        assertEquals(updatedExpense, modelManager.getSelectedExpense());
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredExpenseList().remove(0);
    }

    @Test
    public void setSelectedExpense_expenseNotInFilteredExpenseList_throwsExpenseNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        modelManager.setSelectedExpense(DUCK_RICE);
    }

    @Test
    public void setSelectedExpense_expenseInFilteredExpenseList_setsSelectedExpense() {
        modelManager.addExpense(DUCK_RICE);
        assertEquals(Collections.singletonList(DUCK_RICE), modelManager.getFilteredExpenseList());
        modelManager.setSelectedExpense(DUCK_RICE);
        assertEquals(DUCK_RICE, modelManager.getSelectedExpense());
    }

    //==== Debt-related tests ================================================================================

    @Test
    public void hasDebt_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasDebt(null);
    }

    @Test
    public void hasDebt_debtNotInFinanceTracker_returnsFalse() {
        assertFalse(modelManager.hasDebt(AMY));
    }

    @Test
    public void hasDebt_debtInFinanceTracker_returnsTrue() {
        modelManager.addDebt(AMY);
        assertTrue(modelManager.hasDebt(AMY));
    }

    @Test
    public void deleteDebt_debtIsSelectedAndFirstDebtInFilteredDebtList_selectionCleared() {
        modelManager.addDebt(AMY);
        modelManager.setSelectedDebt(AMY);
        modelManager.deleteDebt(AMY);
        assertEquals(null, modelManager.getSelectedDebt());
    }

    @Test
    public void deleteDebt_debtIsSelectedAndSecondDebtInFilteredDebtList_firstDebtSelected() {
        modelManager.addDebt(AMY);
        modelManager.addDebt(BOB);
        assertEquals(Arrays.asList(AMY, BOB), modelManager.getFilteredDebtList());
        modelManager.setSelectedDebt(BOB);
        modelManager.deleteDebt(BOB);
        assertEquals(AMY, modelManager.getSelectedDebt());
    }

    @Test
    public void setDebt_debtIsSelected_selectedDebtUpdated() {
        modelManager.addDebt(AMY);
        modelManager.setSelectedDebt(AMY);
        Debt updatedDebt = new DebtBuilder(AMY).withPersonOwed(VALID_NAME_DEBT).build();
        modelManager.setDebt(AMY, updatedDebt);
        assertEquals(updatedDebt, modelManager.getSelectedDebt());
    }

    @Test
    public void getFilteredDebtList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredDebtList().remove(0);
    }

    @Test
    public void setSelectedDebt_debtNotInFilteredDebtList_throwsDebtNotFoundException() {
        thrown.expect(DebtNotFoundException.class);
        modelManager.setSelectedDebt(AMY);
    }

    @Test
    public void setSelectedDebt_debtInFilteredDebtList_setsSelectedDebt() {
        modelManager.addDebt(AMY);
        assertEquals(Collections.singletonList(AMY), modelManager.getFilteredDebtList());
        modelManager.setSelectedDebt(AMY);
        assertEquals(AMY, modelManager.getSelectedDebt());
    }

    //==== Budget-related tests ==============================================================================

    //==== Recurring-related tests ===========================================================================

    @Test
    public void equals() {
        FinanceTracker financeTracker = new FinanceTrackerBuilder().withExpense(DUCK_RICE).withExpense(TAXI).build();
        FinanceTracker differentFinanceTracker = new FinanceTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(financeTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(financeTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different financeTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFinanceTracker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = DUCK_RICE.getName().name.split("\\s+");
        modelManager.updateFilteredExpenseList(new NameContainsKeywordsPredicateForExpense(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFinanceTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, differentUserPrefs)));
    }
}
