package seedu.address.logic.commands.recurringcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;

import org.junit.Test;

import seedu.address.logic.commands.recurringcommands.EditRecurringCommand.EditRecurringDescriptor;
import seedu.address.testutil.EditRecurringDescriptorBuilder;

public class EditRecurringDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditRecurringCommand.EditRecurringDescriptor descriptorWithSameValues =
                new EditRecurringDescriptor(DESC_RECURRING);
        assertTrue(DESC_RECURRING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_RECURRING.equals(DESC_RECURRING));

        // null -> returns false
        assertFalse(DESC_RECURRING.equals(null));

        // different types -> returns false
        assertFalse(DESC_RECURRING.equals(5));

        // different name -> returns false
        EditRecurringCommand.EditRecurringDescriptor editedRecurring =
                new EditRecurringDescriptorBuilder(DESC_RECURRING).withName(VALID_NAME_DEBT).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different amount -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withAmount(VALID_AMOUNT_DEBT).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different category -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withCategory(VALID_CATEGORY_DEBT).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different date -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withDate(VALID_DEADLINE_DEBT).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different remarks -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withRemarks(VALID_REMARKS_EXPENSE).build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different frequency -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withOccurrence("2").build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));

        // different frequency -> returns false
        editedRecurring = new EditRecurringDescriptorBuilder(DESC_RECURRING).withFrequency("D").build();
        assertFalse(DESC_RECURRING.equals(editedRecurring));
    }
}
