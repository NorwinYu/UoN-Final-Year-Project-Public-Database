package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_DEBT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_DEBT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.debtcommands.EditDebtCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.testutil.EditDebtDescriptorBuilder;

public class EditDebtCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDebtCommand.MESSAGE_USAGE);

    private EditDebtCommandParser parser = new EditDebtCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_DEBT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditDebtCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_DEBT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_DEBT, MESSAGE_INVALID_FORMAT);

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
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC_FORMAT,
                Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC_DATE,
                Date.MESSAGE_DEADLINE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_CATEGORY_DESC
                        + VALID_DEADLINE_DEBT + VALID_AMOUNT_DEBT, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_necessaryFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_DEBT;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_DEBT
                + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT + NAME_DESC_DEBT + REMARKS_DESC_DEBT;

        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).withAmount(VALID_AMOUNT_DEBT)
                .withCategory(VALID_CATEGORY_DEBT).withDeadline(VALID_DEADLINE_DEBT)
                .withRemarks(VALID_REMARKS_DEBT).build();
        EditDebtCommand expectedCommand = new EditDebtCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_DEBT;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT;

        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withAmount(VALID_AMOUNT_DEBT).withCategory(VALID_CATEGORY_DEBT).build();
        EditDebtCommand expectedCommand = new EditDebtCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        // person owed
        Index targetIndex = INDEX_THIRD_DEBT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_DEBT;
        EditDebtCommand.EditDebtDescriptor descriptor = new EditDebtDescriptorBuilder()
                .withPersonOwed(VALID_NAME_DEBT).build();
        EditDebtCommand expectedCommand = new EditDebtCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_DEBT;
        descriptor = new EditDebtDescriptorBuilder().withAmount(VALID_AMOUNT_DEBT).build();
        expectedCommand = new EditDebtCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_DEBT;
        descriptor = new EditDebtDescriptorBuilder().withCategory(VALID_CATEGORY_DEBT).build();
        expectedCommand = new EditDebtCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_DEBT;
        descriptor = new EditDebtDescriptorBuilder().withDeadline(VALID_DEADLINE_DEBT).build();
        expectedCommand = new EditDebtCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = targetIndex.getOneBased() + REMARKS_DESC_DEBT;
        descriptor = new EditDebtDescriptorBuilder().withRemarks(VALID_REMARKS_DEBT).build();
        expectedCommand = new EditDebtCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() throws ParseException {
        Index targetIndex = INDEX_FIRST_DEBT;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_DEBT + DEADLINE_DESC_DEBT + CATEGORY_DESC_DEBT
                + REMARKS_DESC_DEBT + AMOUNT_DESC_DEBT_2 + DEADLINE_DESC_DEBT_2 + CATEGORY_DESC_DEBT_2
                + REMARKS_DESC_DEBT_2;

        assertParseFailure(parser, userInput, MESSAGE_REPEATED_PREFIX_COMMAND);
    }

}
