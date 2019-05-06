package seedu.address.testutil;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_CATEGORY = "food";
    public static final String DEFAULT_AMOUNT = "500";
    public static final String DEFAULT_START_DATE = "01-06-2021";
    public static final String DEFAULT_END_DATE = "30-06-2021";
    public static final String DEFAULT_REMARKS = "save up for trip";

    private Category category;
    private Amount amount;
    private Date startDate;
    private Date endDate;
    private String remarks;

    public BudgetBuilder() {
        category = Category.valueOf(DEFAULT_CATEGORY.toUpperCase());
        amount = new Amount(DEFAULT_AMOUNT);
        startDate = new Date(DEFAULT_START_DATE);
        endDate = new Date(DEFAULT_END_DATE);
        remarks = DEFAULT_REMARKS;
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        category = budgetToCopy.getCategory();
        amount = budgetToCopy.getAmount();
        startDate = budgetToCopy.getStartDate();
        endDate = budgetToCopy.getEndDate();
        remarks = budgetToCopy.getRemarks();
    }

    /**
     * Sets the {@code Category} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withCategory(String category) {
        this.category = Category.valueOf(category.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withStartDate(String startDate) {
        this.startDate = new Date(startDate);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withEndDate(String endDate) {
        this.endDate = new Date(endDate);
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Budget build() {
        return new Budget(category, amount, startDate, endDate, remarks, 0, 0);
    }
}
