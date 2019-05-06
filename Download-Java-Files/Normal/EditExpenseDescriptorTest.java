package seedu.address.logic.commands.expensecommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseCommand.EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_EXPENSE);
        assertTrue(DESC_EXPENSE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EXPENSE.equals(DESC_EXPENSE));

        // null -> returns false
        assertFalse(DESC_EXPENSE.equals(null));

        // different types -> returns false
        assertFalse(DESC_EXPENSE.equals(5));

        // different name -> returns false
        EditExpenseCommand.EditExpenseDescriptor editedExpense = new EditExpenseDescriptorBuilder(DESC_EXPENSE)
                .withName(VALID_NAME_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedExpense));

        // different phone -> returns false
        editedExpense = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedExpense));

        // different email -> returns false
        editedExpense = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withCategory(VALID_CATEGORY_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedExpense));

        // different address -> returns false
        editedExpense = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withDate(VALID_DEADLINE_DEBT).build();
        assertFalse(DESC_EXPENSE.equals(editedExpense));

        // different tags -> returns false
        editedExpense = new EditExpenseDescriptorBuilder(DESC_EXPENSE).withRemarks(VALID_REMARKS_EXPENSE).build();
        assertFalse(DESC_EXPENSE.equals(editedExpense));
    }
}
