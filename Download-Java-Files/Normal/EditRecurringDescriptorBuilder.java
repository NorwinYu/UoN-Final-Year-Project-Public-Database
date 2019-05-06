package seedu.address.testutil;

import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand.EditRecurringDescriptor;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;

/**
 * A utility class to help with building EditRecurringDescriptor objects.
 */
public class EditRecurringDescriptorBuilder {

    private EditRecurringCommand.EditRecurringDescriptor descriptor;

    public EditRecurringDescriptorBuilder() {
        descriptor = new EditRecurringDescriptor();
    }

    public EditRecurringDescriptorBuilder(EditRecurringDescriptor descriptor) {
        this.descriptor = new EditRecurringDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecurringDescriptor} with fields containing {@code recurring}'s details
     */
    public EditRecurringDescriptorBuilder(Recurring recurring) {
        descriptor = new EditRecurringDescriptor();
        descriptor.setName(recurring.getName());
        descriptor.setAmount(recurring.getAmount());
        descriptor.setCategory(recurring.getCategory());
        descriptor.setDate(recurring.getDate());
        descriptor.setRemarks(recurring.getRemarks());
        descriptor.setFrequency(recurring.getFrequency());
        descriptor.setOccurrence(recurring.getOccurrence());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(Category.valueOf(category.toUpperCase()));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the remarks of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withRemarks(String remarks) {
        descriptor.setRemarks(remarks);
        return this;
    }

    /**
     * Sets the frequency of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withFrequency(String frequency) {
        descriptor.setFrequency(new Frequency(frequency));
        return this;
    }

    /**
     * Sets the occurrence of the {@code EditRecurringDescriptor} that we are building.
     */
    public EditRecurringDescriptorBuilder withOccurrence(String occurrence) {
        descriptor.setOccurrence(new Occurrence(occurrence));
        return this;
    }

    public EditRecurringDescriptor build() {
        return descriptor;
    }
}
