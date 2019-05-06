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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_RECURRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RECURRING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCURRENCE_RECURRING_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRecurrings.RECURRING;
import static seedu.address.testutil.TypicalRecurrings.RECURRING_WITHOUT_REMARKS;

import org.junit.Test;

import seedu.address.logic.commands.recurringcommands.AddRecurringCommand;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;
import seedu.address.testutil.RecurringBuilder;

public class AddRecurringCommandParserTest {
    private AddRecurringCommandParser parser = new AddRecurringCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recurring expectedRecurring = new RecurringBuilder(RECURRING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                        + OCCURRENCE_DESC_RECURRING + REMARKS_DESC_RECURRING,
                new AddRecurringCommand(expectedRecurring));

        assertParseSuccess(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                        + OCCURRENCE_DESC_RECURRING + REMARKS_DESC_RECURRING,
                new AddRecurringCommand(expectedRecurring));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Recurring expectedRecurring = new RecurringBuilder(RECURRING_WITHOUT_REMARKS).build();
        assertParseSuccess(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                new AddRecurringCommand(expectedRecurring));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecurringCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_RECURRING_2 + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_RECURRING + VALID_CATEGORY_RECURRING_2 + DATE_DESC_RECURRING
                + AMOUNT_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_RECURRING + VALID_AMOUNT_RECURRING_2 + DATE_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE_RECURRING_2 + NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING, expectedMessage);

        // missing frequency prefix
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + DATE_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + VALID_FREQUENCY_RECURRING_2 + OCCURRENCE_DESC_RECURRING, expectedMessage);

        // missing occurrence prefix
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + DATE_DESC_RECURRING
                + CATEGORY_DESC_RECURRING + FREQUENCY_DESC_RECURRING + VALID_OCCURRENCE_RECURRING_2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_RECURRING_2 + VALID_AMOUNT_RECURRING_2 + VALID_CATEGORY_RECURRING_2
                + VALID_DATE_RECURRING_2 + VALID_FREQUENCY_RECURRING_2 + VALID_OCCURRENCE_RECURRING_2, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_RECURRING + INVALID_AMOUNT_DESC + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                Amount.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + INVALID_CATEGORY_DESC
                + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                Category.MESSAGE_CONSTRAINTS);

        // invalid date format
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + INVALID_DATE_DESC_FORMAT + REMARKS_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                + OCCURRENCE_DESC_RECURRING, Date.MESSAGE_CONSTRAINTS);

        // invalid frequency
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING + INVALID_FREQUENCY_DESC
                + OCCURRENCE_DESC_RECURRING, Frequency.MESSAGE_CONSTRAINTS);

        // invalid occurrence
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING + FREQUENCY_DESC_RECURRING
                + INVALID_OCCURRENCE_DESC, Occurrence.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING + INVALID_DATE_DESC_FORMAT,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecurringCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        // multiple categories - not accepted
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple names - not accepted
        assertParseFailure(parser, NAME_DESC_RECURRING + NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple frequency
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple occurrence
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING
                        + CATEGORY_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple dates - not accepted
        assertParseFailure(parser, NAME_DESC_RECURRING + AMOUNT_DESC_RECURRING + CATEGORY_DESC_RECURRING
                        + DATE_DESC_RECURRING + DATE_DESC_RECURRING + REMARKS_DESC_RECURRING
                        + FREQUENCY_DESC_RECURRING + OCCURRENCE_DESC_RECURRING,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
