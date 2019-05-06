package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_BEFORE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_BUDGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;
import seedu.address.testutil.EditBudgetDescriptorBuilder;

public class EditBudgetCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE);

    private EditBudgetCommandParser parser = new EditBudgetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no category prefix
        assertParseFailure(parser, VALID_CATEGORY_BUDGET + AMOUNT_DESC_BUDGET,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBudgetCommand.MESSAGE_USAGE));

        // no category specified
        assertParseFailure(parser, AMOUNT_DESC_BUDGET, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBudgetCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, CATEGORY_DESC_BUDGET, EditBudgetCommand.MESSAGE_NOT_EDITED);

        // no category and no field specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid category
        assertParseFailure(parser, INVALID_CATEGORY_DESC + AMOUNT_DESC_BUDGET, Category.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid amount
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);
        // invalid start date format
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_STARTDATE_DESC_FORMAT,
                Date.MESSAGE_CONSTRAINTS);
        //invalid start date
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_STARTDATE_DESC_EXIST,
                Date.MESSAGE_DATE_DOES_NOT_EXIST);
        //invalid end date format
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_ENDDATE_DESC_FORMAT,
                Date.MESSAGE_CONSTRAINTS);
        //invalid end date
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_ENDDATE_DESC_EXIST,
                Date.MESSAGE_DATE_DOES_NOT_EXIST);
        //invalid end date before today
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_ENDDATE_DESC_BEFORE_TODAY,
                Budget.MESSAGE_CONSTRAINTS_END_DATE_AFTER_TODAY);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_AMOUNT_DESC + INVALID_STARTDATE_DESC_FORMAT
                        + VALID_ENDDATE_BUDGET + VALID_REMARKS_BUDGET,
                Amount.MESSAGE_CONSTRAINTS);
    }

    // pass
    @Test
    public void parse_necessaryFieldsSpecified_success() {
        Category targetCategory = Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase());
        String userInput = CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET
                + REMARKS_DESC_BUDGET;

        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_AMOUNT_BUDGET).withStartDate(
                VALID_STARTDATE_BUDGET).withEndDate(VALID_ENDDATE_BUDGET).withRemarks(VALID_REMARKS_BUDGET).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetCategory, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Category targetCategory = Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase());
        String userInput = CATEGORY_DESC_BUDGET + STARTDATE_DESC_BUDGET + REMARKS_DESC_BUDGET;

        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withStartDate(VALID_STARTDATE_BUDGET).withRemarks(VALID_REMARKS_BUDGET).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetCategory, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // amount
        Category targetCategory = Category.valueOf(VALID_CATEGORY_BUDGET.toUpperCase());
        String userInput = CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET;
        EditBudgetCommand.EditBudgetDescriptor descriptor = new EditBudgetDescriptorBuilder()
                .withAmount(VALID_AMOUNT_BUDGET).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(targetCategory, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = CATEGORY_DESC_BUDGET + STARTDATE_DESC_BUDGET;
        descriptor = new EditBudgetDescriptorBuilder().withStartDate(VALID_STARTDATE_BUDGET).build();
        expectedCommand = new EditBudgetCommand(targetCategory, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date
        userInput = CATEGORY_DESC_BUDGET + ENDDATE_DESC_BUDGET;
        descriptor = new EditBudgetDescriptorBuilder().withEndDate(VALID_ENDDATE_BUDGET).build();
        expectedCommand = new EditBudgetCommand(targetCategory, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = CATEGORY_DESC_BUDGET + REMARKS_DESC_BUDGET;
        descriptor = new EditBudgetDescriptorBuilder().withRemarks(VALID_REMARKS_BUDGET).build();
        expectedCommand = new EditBudgetCommand(targetCategory, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = CATEGORY_DESC_BUDGET + AMOUNT_DESC_EXPENSE + AMOUNT_DESC_BUDGET;

        assertParseFailure(parser, userInput, MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
