package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;


/**
 * Jackson-friendly version of {@link seedu.address.model.table.Table}.
 */
class JsonAdaptedTable {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Table's %s field is missing!";

    private final String tableNumber;
    private final String tableStatus;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTable(@JsonProperty("tableNumber") String tableNumber,
                            @JsonProperty("tableStatus") String tableStatus) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    /**
     * Converts a given {@code MenuItem} into this class for Jackson use.
     */
    public JsonAdaptedTable(Table table) {
        tableNumber = table.getTableNumber().toString();
        tableStatus = table.getTableStatus().numberOfSeats;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Table toModelType() throws IllegalValueException {
        if (tableNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!TableNumber.isValidTableNumber(tableNumber)) {
            throw new IllegalValueException(TableNumber.MESSAGE_CONSTRAINTS);
        }
        final TableNumber modelTableNumber = new TableNumber(tableNumber);

        if (tableStatus == null) {
            throw new IllegalValueException(TableStatus.MESSAGE_CONSTRAINTS);
        }

        if (!TableStatus.isValidTableStatus(tableStatus)) {
            throw new IllegalValueException(TableStatus.MESSAGE_CONSTRAINTS);
        }
        final TableStatus modelTableStatus = new TableStatus(tableStatus);

        return new Table(modelTableNumber, modelTableStatus);
    }

}
