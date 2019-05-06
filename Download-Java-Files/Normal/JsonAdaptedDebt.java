package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;

/**
 * Jackson-friendly version of {@link Debt}.
 */
class JsonAdaptedDebt {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Debt's %s field is missing!";

    private final String personOwed;
    private final String amount;
    private final String category;
    private final String deadline;
    private final String remarks;

    /**
     * Constructs a {@code JsonAdaptedDebt} with the given debt details.
     */
    @JsonCreator
    public JsonAdaptedDebt(@JsonProperty("personOwed") String personOwed, @JsonProperty("amount") String amount,
                           @JsonProperty("deadline") String deadline, @JsonProperty("category") String category,
                           @JsonProperty("remarks") String remarks) {
        this.personOwed = personOwed;
        this.amount = amount;
        this.category = category;
        this.deadline = deadline;
        this.remarks = remarks;
    }

    /**
     * Converts a given {@code Debt} into this class for Jackson use.
     */
    public JsonAdaptedDebt(Debt source) {
        personOwed = source.getPersonOwed().name;
        amount = source.getAmount().toString();
        category = source.getCategory().toString();
        deadline = source.getDeadline().toString();
        remarks = source.getRemarks();
    }

    /**
     * Converts this Jackson-friendly adapted debt object into the model's {@code Debt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted debt.
     */
    public Debt toModelType() throws IllegalValueException {
        if (personOwed == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(personOwed)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelPersonOwed = new Name(personOwed);

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

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (Date.isValidDate(deadline) == "format") {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDeadline = new Date(deadline);

        final String modelRemarks = remarks;

        return new Debt(modelPersonOwed, modelAmount, modelDeadline, modelCategory, modelRemarks);
    }

}
