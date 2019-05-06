package seedu.address.testutil;

import seedu.address.logic.commands.budgetcommands.EditBudgetCommand.EditBudgetDescriptor;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

/**
 * A utility class to help with building EditBudgetDescriptor objects.
 */
public class EditBudgetDescriptorBuilder {
    private EditBudgetDescriptor descriptor;

    public EditBudgetDescriptorBuilder() {
        descriptor = new EditBudgetDescriptor();
    }

    public EditBudgetDescriptorBuilder(EditBudgetDescriptor descriptor) {
        this.descriptor = new EditBudgetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBudgetDescriptor} with fields containing {@code budge}'s details
     */
    public EditBudgetDescriptorBuilder(Budget budget) {
        descriptor = new EditBudgetDescriptor();
        descriptor.setAmount(budget.getAmount());
        descriptor.setStartDate(budget.getStartDate());
        descriptor.setEndDate(budget.getEndDate());
        descriptor.setRemarks(budget.getRemarks());
    }

    /**
     * Sets the {@code Amount} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withStartDate(String date) {
        descriptor.setStartDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withEndDate(String date) {
        descriptor.setEndDate(new Date(date));
        return this;
    }

    /**
     * Sets the remarks of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withRemarks(String remarks) {
        descriptor.setRemarks(remarks);
        return this;
    }

    public EditBudgetDescriptor build() {
        return descriptor;
    }
}
