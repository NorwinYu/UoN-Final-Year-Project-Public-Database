package seedu.address.testutil;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final String DEFAULT_NAME = "MRT";
    public static final String DEFAULT_AMOUNT = "2.50";
    public static final String DEFAULT_CATEGORY = "transport";
    public static final String DEFAULT_DATE = "03-03-2019";
    public static final String DEFAULT_REMARKS = null;

    private Name name;
    private Amount amount;
    private Category category;
    private Date date;
    private String remarks;

    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        category = Category.valueOf(DEFAULT_CATEGORY.toUpperCase());
        date = new Date(DEFAULT_DATE);
        remarks = DEFAULT_REMARKS;
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        amount = expenseToCopy.getAmount();
        category = expenseToCopy.getCategory();
        date = expenseToCopy.getDate();
        remarks = expenseToCopy.getRemarks();
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCategory(String category) {
        this.category = Category.valueOf(category.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Expense build() {
        return new Expense(name, amount, date, category, remarks);
    }

}
