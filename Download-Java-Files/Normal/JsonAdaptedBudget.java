package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String amount;
    private final String category;
    private final String startDate;
    private final String endDate;
    private final String remarks;
    private final String totalSpent;
    private final String percentage;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount, @JsonProperty("category") String category,
                             @JsonProperty("startDate") String startDate, @JsonProperty("endDate") String endDate,
                             @JsonProperty("remarks") String remarks, @JsonProperty("totalSpent") String totalSpent,
                             @JsonProperty("percentage") String percentage) {
        this.startDate = startDate;
        this.amount = amount;
        this.category = category;
        this.endDate = endDate;
        this.remarks = remarks;
        this.totalSpent = totalSpent;
        this.percentage = percentage;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        amount = source.getAmount().toString();
        category = source.getCategory().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        remarks = source.getRemarks();
        totalSpent = source.getTotalSpentString();
        percentage = source.getPercentageString();
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budget.
     */
    public Budget toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValid(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelCategory = Category.valueOf(category.toUpperCase());

        // start date is optional hence not required to check if null
        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (Date.isValidDate(startDate) == "format" || Date.isValidDate(endDate) == "format") {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelStartDate = new Date(startDate);
        final Date modelEndDate = new Date(endDate);

        final String modelRemarks = remarks;

        final int modelTotalSpent = Integer.parseInt(totalSpent);

        final double modelPercentage = Double.parseDouble(percentage);

        return new Budget(modelCategory, modelAmount, modelStartDate, modelEndDate, modelRemarks, modelTotalSpent,
                modelPercentage);
    }

}
