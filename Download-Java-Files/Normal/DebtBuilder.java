package seedu.address.testutil;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;

/**
 * A utility class to help with building Debt objects.
 */
public class DebtBuilder {
    public static final String DEFAULT_PERSON_OWED = "John Doe";
    public static final String DEFAULT_AMOUNT = "50.00";
    public static final String DEFAULT_CATEGORY = "work";
    public static final String DEFAULT_DEADLINE = "12-12-2019";
    public static final String DEFAULT_REMARKS = "";

    private Name personOwed;
    private Amount amount;
    private Category category;
    private Date deadline;
    private String remarks;

    public DebtBuilder() {
        personOwed = new Name(DEFAULT_PERSON_OWED);
        amount = new Amount(DEFAULT_AMOUNT);
        category = Category.valueOf(DEFAULT_CATEGORY.toUpperCase());
        deadline = new Date(DEFAULT_DEADLINE);
        remarks = DEFAULT_REMARKS;
    }

    /**
     * Initializes the DebtBuilder with the data of {@code debtToCopy}.
     */
    public DebtBuilder(Debt debtToCopy) {
        personOwed = debtToCopy.getPersonOwed();
        amount = debtToCopy.getAmount();
        category = debtToCopy.getCategory();
        deadline = debtToCopy.getDeadline();
        remarks = debtToCopy.getRemarks();
    }

    /**
     * Sets the {@code Name} of the {@code Debt} that we are building.
     */
    public DebtBuilder withPersonOwed(String personOwed) {
        this.personOwed = new Name(personOwed);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Debt} that we are building.
     */
    public DebtBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Debt} that we are building.
     */
    public DebtBuilder withCategory(String category) {
        this.category = Category.valueOf(category.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Debt} that we are building.
     */
    public DebtBuilder withDeadline(String deadline) {
        this.deadline = new Date(deadline);
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code Debt} that we are building.
     */
    public DebtBuilder withRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Debt build() {
        return new Debt(personOwed, amount, deadline, category, remarks);
    }
}
