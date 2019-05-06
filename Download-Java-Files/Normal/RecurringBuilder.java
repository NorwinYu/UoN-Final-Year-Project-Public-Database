package seedu.address.testutil;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;

/**
 * A utility class to help with building Recurring objects.
 */
public class RecurringBuilder {
    public static final String DEFAULT_NAME = "Phone Bill";
    public static final String DEFAULT_AMOUNT = "50.00";
    public static final String DEFAULT_CATEGORY = "utilities";
    public static final String DEFAULT_DATE = "23-02-2019";
    public static final String DEFAULT_REMARKS = "";
    public static final String DEFAULT_FREQUENCY = "M";
    public static final String DEFAULT_OCCURRENCE = "24";

    private Name name;
    private Amount amount;
    private Category category;
    private Date date;
    private String remarks;
    private Frequency frequency;
    private Occurrence occurrence;

    public RecurringBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        category = Category.valueOf(DEFAULT_CATEGORY.toUpperCase());
        date = new Date(DEFAULT_DATE);
        remarks = DEFAULT_REMARKS;
        frequency = new Frequency(DEFAULT_FREQUENCY);
        occurrence = new Occurrence(DEFAULT_OCCURRENCE);
    }

    /**
     * Initializes the RecurringBuilder with the data of {@code recurringToCopy}.
     */
    public RecurringBuilder(Recurring recurringToCopy) {
        name = recurringToCopy.getName();
        amount = recurringToCopy.getAmount();
        category = recurringToCopy.getCategory();
        date = recurringToCopy.getDate();
        remarks = recurringToCopy.getRemarks();
        frequency = recurringToCopy.getFrequency();
        occurrence = recurringToCopy.getOccurrence();
    }

    /**
     * Sets the {@code Name} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withCategory(String category) {
        this.category = Category.valueOf(category.toUpperCase());
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    /**
     * Sets the {@code Frequency} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withFrequency(String frequency) {
        this.frequency = new Frequency(frequency);
        return this;
    }

    /**
     * Sets the {@code Occurrence} of the {@code Recurring} that we are building.
     */
    public RecurringBuilder withOccurrence(String occurrence) {
        this.occurrence = new Occurrence(occurrence);
        return this;
    }

    public Recurring build() {
        return new Recurring(name, amount, date, category, remarks, frequency, occurrence);
    }
}






