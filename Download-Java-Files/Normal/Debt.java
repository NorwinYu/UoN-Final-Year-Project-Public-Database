package seedu.address.model.debt;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;

/**
 * Represents a Debt in the finance tracker.
 * Guarantees: field values are validated, immutable.
 */
public class Debt {
    // Compulsory fields
    private Name personOwed;
    private Amount amount;
    private Category category;
    private Date deadline;

    // Optional fields
    private String remarks;

    /**
     * Initializes a newly created Debt object.
     */
    public Debt(Name personOwed, Amount amount, Date deadline, Category category, String remarks) {
        requireAllNonNull(personOwed, amount, deadline, category);
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = deadline;

        if (remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = remarks;
        }
    }

    public Name getPersonOwed() {
        return personOwed;
    }

    public Amount getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getRemarks() {
        return remarks;
    }

    /**
     * Returns true if both debts have the same personOwed and amount.
     * This defines a weaker notion of equality between two debts.
     */
    public boolean isSameDebt(Debt otherDebt) {
        if (otherDebt == this) {
            return true;
        }

        return otherDebt != null
                && otherDebt.getPersonOwed().equals(this.personOwed)
                && otherDebt.getAmount().equals(this.amount);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Debt)) {
            return false;
        }

        Debt otherExpense = (Debt) other;
        return otherExpense.getPersonOwed().equals(getPersonOwed())
                && otherExpense.getAmount().equals(getAmount())
                && otherExpense.getDeadline().equals(getDeadline())
                && otherExpense.getCategory().equals(getCategory())
                && otherExpense.getRemarks().equals(getRemarks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personOwed, amount, category, deadline, remarks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Person owed: ")
                .append(personOwed.toString())
                .append(" Amount owed: ")
                .append(amount.toString())
                .append(" Category: ")
                .append(category.toString())
                .append(" Deadline: ")
                .append(deadline.toString());

        if (!remarks.equals("")) {
            builder.append(" Remarks: ").append(remarks);
        }
        return builder.toString();
    }

}
