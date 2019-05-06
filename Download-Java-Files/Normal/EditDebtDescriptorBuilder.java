package seedu.address.testutil;

import seedu.address.logic.commands.debtcommands.EditDebtCommand.EditDebtDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;

/**
 * A utility class to help with building EditDebtDescriptor objects.
 */
public class EditDebtDescriptorBuilder {

    private EditDebtDescriptor descriptor;

    public EditDebtDescriptorBuilder() {
        descriptor = new EditDebtDescriptor();
    }

    public EditDebtDescriptorBuilder(EditDebtDescriptor descriptor) throws ParseException {
        this.descriptor = new EditDebtDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDebtDescriptor} with fields containing {@code debt}'s details
     */
    public EditDebtDescriptorBuilder(Debt debt) throws ParseException {
        descriptor = new EditDebtDescriptor();
        descriptor.setPersonOwed(debt.getPersonOwed());
        descriptor.setAmount(debt.getAmount());
        descriptor.setCategory(debt.getCategory());
        descriptor.setDeadline(debt.getDeadline());
        descriptor.setRemarks(debt.getRemarks());
    }

    /**
     * Sets the {@code Name} of the {@code EditDebtDescriptor} that we are building.
     */
    public EditDebtDescriptorBuilder withPersonOwed(String name) {
        descriptor.setPersonOwed(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditDebtDescriptor} that we are building.
     */
    public EditDebtDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditDebtDescriptor} that we are building.
     */
    public EditDebtDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(Category.valueOf(category.toUpperCase()));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditDebtDescriptor} that we are building.
     */
    public EditDebtDescriptorBuilder withDeadline(String date) throws ParseException {
        descriptor.setDeadline(new Date(date));
        return this;
    }

    /**
     * Sets the remarks of the {@code EditDebtDescriptor} that we are building.
     */
    public EditDebtDescriptorBuilder withRemarks(String remarks) {
        descriptor.setRemarks(remarks);
        return this;
    }

    public EditDebtDescriptor build() {
        return descriptor;
    }
}
