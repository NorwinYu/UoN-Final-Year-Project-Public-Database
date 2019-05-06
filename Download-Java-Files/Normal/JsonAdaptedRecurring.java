package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;

/**
 * Jackson-friendly version of {@link Recurring}.
 */
class JsonAdaptedRecurring {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recurring's %s field is missing!";

    private final String name;
    private final String amount;
    private final String category;
    private final String date;
    private final String remarks;
    private final String frequency;
    private final String occurrence;
    private final String lastConvertedDate;

    /**
     * Constructs a {@code JsonAdaptedRecurring} with the given recurring details.
     */
    @JsonCreator
    public JsonAdaptedRecurring(@JsonProperty("name") String name, @JsonProperty("amount") String amount,
                                @JsonProperty("date") String date, @JsonProperty("category") String category,
                                @JsonProperty("remarks") String remarks, @JsonProperty("frequency") String frequency,
                                @JsonProperty("occurrence") String occurrence,
                                @JsonProperty("lastConvertedDate") String lastConvertedDate) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.remarks = remarks;
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.lastConvertedDate = lastConvertedDate;
    }

    /**
     * Converts a given {@code Recurring} into this class for Jackson use.
     */
    public JsonAdaptedRecurring(Recurring source) {
        name = source.getName().name;
        amount = source.getAmount().toString();
        category = source.getCategory().toString();
        date = source.getDate().toString();
        remarks = source.getRemarks();
        frequency = source.getFrequency().toString();
        occurrence = source.getOccurrence().toString();
        lastConvertedDate = source.getLastConvertedDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted recurring object into the model's {@code Recurring} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted recurring.
     */
    public Recurring toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (Date.isValidDate(date) == "format") {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final String modelRemarks = remarks;

        if (frequency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Frequency.class.getSimpleName()));
        }
        if (!Frequency.isValidFrequency(frequency)) {
            throw new IllegalValueException(Frequency.MESSAGE_CONSTRAINTS);
        }
        final Frequency modelFrequency = new Frequency(frequency);

        if (occurrence == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Occurrence.class.getSimpleName()));
        }
        if (!Occurrence.isValidOccurrence(occurrence)) {
            throw new IllegalValueException(Occurrence.MESSAGE_CONSTRAINTS);
        }
        final Occurrence modelOccurrence = new Occurrence(occurrence);

        final String modelLastConvertedDate = lastConvertedDate;

        return new Recurring(modelName, modelAmount, modelDate, modelCategory, modelRemarks, modelFrequency,
                modelOccurrence, modelLastConvertedDate);
    }

}
