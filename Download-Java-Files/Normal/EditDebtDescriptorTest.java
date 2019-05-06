package seedu.address.logic.commands.debtcommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT_2;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditDebtDescriptorBuilder;

public class EditDebtDescriptorTest {

    @Test
    public void equals() throws ParseException {
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).withAmount(VALID_AMOUNT_DEBT).withCategory(VALID_CATEGORY_DEBT)
                .withDeadline(VALID_DEADLINE_DEBT).withRemarks(VALID_REMARKS_DEBT).build();

        // same values -> returns true
        EditDebtCommand.EditDebtDescriptor descriptorWithSameValues = new EditDebtCommand
                .EditDebtDescriptor(descriptor);
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different name -> returns false
        EditDebtCommand.EditDebtDescriptor editedDebt = new EditDebtDescriptorBuilder(descriptor)
                .withPersonOwed(VALID_NAME_DEBT_2).build();
        assertFalse(descriptor.equals(editedDebt));

        // different amount -> returns false
        editedDebt = new EditDebtDescriptorBuilder(descriptor).withAmount(VALID_AMOUNT_DEBT_2).build();
        assertFalse(descriptor.equals(editedDebt));

        // different category -> returns false
        editedDebt = new EditDebtDescriptorBuilder(descriptor).withCategory(VALID_CATEGORY_DEBT_2).build();
        assertFalse(descriptor.equals(editedDebt));

        // different deadline -> returns false
        editedDebt = new EditDebtDescriptorBuilder(descriptor).withDeadline(VALID_DEADLINE_DEBT_2).build();
        assertFalse(descriptor.equals(editedDebt));

        // different remarks -> returns false
        editedDebt = new EditDebtDescriptorBuilder(descriptor).withRemarks(VALID_REMARKS_DEBT_2).build();
        assertFalse(descriptor.equals(editedDebt));
    }
}
