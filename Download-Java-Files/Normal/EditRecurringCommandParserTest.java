package seedu.address.logic.parser.recurringparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OCCURRENCE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.OCCURRENCE_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURRENCE_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_RECURRING_2;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECURRING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECURRING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RECURRING;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.testutil.EditRecurringDescriptorBuilder;

public class EditRecurringCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRecurringCommand.MESSAGE_USAGE);

    private EditRecurringCommandParser parser = new EditRecurringCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_RECURRING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditRecurringCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_RECURRING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_RECURRING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC,
                Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC,
                Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser, "1" + INVALID_DATE_DESC_FORMAT,
                Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_FREQUENCY_DESC,
                Frequency.MESSAGE_CONSTRAINTS); // invalid frequency
        assertParseFailure(parser, "1" + INVALID_OCCURRENCE_DESC,
                Occurrence.MESSAGE_CONSTRAINTS); // invalid occurrence

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_CATEGORY_DESC
                + VALID_DATE_RECURRING + VALID_AMOUNT_RECURRING, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_necessaryFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_RECURRING;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + NAME_DESC_RECURRING + REMARKS_DESC_RECURRING;

        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder()
                .withAmount(VALID_AMOUNT_RECURRING_2).withName(VALID_NAME_RECURRING_2)
                .withCategory(VALID_CATEGORY_RECURRING_2).withDate(VALID_DATE_RECURRING_2)
                .withRemarks(VALID_REMARKS_RECURRING_2).build();
        EditRecurringCommand expectedCommand = new EditRecurringCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_RECURRING;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING;

        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder()
                .withAmount(VALID_AMOUNT_RECURRING_2).withCategory(VALID_CATEGORY_RECURRING_2).build();
        EditRecurringCommand expectedCommand = new EditRecurringCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        // name
        Index targetIndex = INDEX_THIRD_RECURRING;
        String userInput = targetIndex.getOneBased() + NAME_DESC_RECURRING;
        EditRecurringCommand.EditRecurringDescriptor descriptor = new EditRecurringDescriptorBuilder()
                .withName(VALID_NAME_RECURRING_2).build();
        EditRecurringCommand expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withAmount(VALID_AMOUNT_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withCategory(VALID_CATEGORY_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withDate(VALID_DATE_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = targetIndex.getOneBased() + REMARKS_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withRemarks(VALID_REMARKS_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // frequency
        userInput = targetIndex.getOneBased() + FREQUENCY_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withFrequency(VALID_FREQUENCY_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // occurrence
        userInput = targetIndex.getOneBased() + OCCURRENCE_DESC_RECURRING;
        descriptor = new EditRecurringDescriptorBuilder().withOccurrence(VALID_OCCURRENCE_RECURRING_2).build();
        expectedCommand = new EditRecurringCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() throws ParseException {
        Index targetIndex = INDEX_FIRST_RECURRING;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_RECURRING + DATE_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + REMARKS_DESC_RECURRING + AMOUNT_DESC_RECURRING + DATE_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + REMARKS_DESC_RECURRING;

        assertParseFailure(parser, userInput, MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
