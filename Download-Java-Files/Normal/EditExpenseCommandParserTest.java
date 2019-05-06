package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_EXPENSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.EditExpenseCommand;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE);

    private EditExpenseCommandParser parser = new EditExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EXPENSE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExpenseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_EXPENSE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_EXPENSE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);
        // invalid category
        assertParseFailure(parser, "1" + INVALID_DATE_DESC_FORMAT, Date.MESSAGE_CONSTRAINTS); // invalid date
        //Deadline not required for editexpense command
        //assertParseFailure(parser, "1" + INVALID_DEADLINE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid tag

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_CATEGORY_DESC + VALID_DATE_EXPENSE
                        + VALID_AMOUNT_EXPENSE,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_necessaryFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EXPENSE;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + NAME_DESC_EXPENSE + REMARKS_DESC_EXPENSE;

        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder().withName(
                VALID_NAME_EXPENSE).withAmount(VALID_AMOUNT_EXPENSE).withCategory(VALID_CATEGORY_EXPENSE).withDate(
                        VALID_DATE_EXPENSE).withRemarks(VALID_REMARKS_EXPENSE).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE;

        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withAmount(VALID_AMOUNT_EXPENSE).withCategory(VALID_CATEGORY_EXPENSE).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EXPENSE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_EXPENSE;
        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withName(VALID_NAME_EXPENSE).build();
        EditExpenseCommand expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_EXPENSE;
        descriptor = new EditExpenseDescriptorBuilder().withAmount(VALID_AMOUNT_EXPENSE).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_EXPENSE;
        descriptor = new EditExpenseDescriptorBuilder().withCategory(VALID_CATEGORY_EXPENSE).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + DATE_DESC_EXPENSE;
        descriptor = new EditExpenseDescriptorBuilder().withDate(VALID_DATE_EXPENSE).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + REMARKS_DESC_EXPENSE;
        descriptor = new EditExpenseDescriptorBuilder().withRemarks(VALID_REMARKS_EXPENSE).build();
        expectedCommand = new EditExpenseCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_EXPENSE + DATE_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + REMARKS_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + DATE_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + REMARKS_DESC_EXPENSE;

        assertParseFailure(parser, userInput, MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
