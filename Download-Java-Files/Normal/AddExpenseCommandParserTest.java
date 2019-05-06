package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EXPENSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalExpenses.CHICKEN_RICE;
import static seedu.address.testutil.TypicalExpenses.EXPENSE_WITHOUT_REMARKS;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.AddExpenseCommand;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

public class AddExpenseCommandParserTest {
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Expense expectedExpense = new ExpenseBuilder(CHICKEN_RICE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                new AddExpenseCommand(expectedExpense));

        assertParseSuccess(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                new AddExpenseCommand(expectedExpense));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Expense expectedExpense = new ExpenseBuilder(EXPENSE_WITHOUT_REMARKS).build();
        assertParseSuccess(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                        + DATE_DESC_EXPENSE, new AddExpenseCommand(expectedExpense));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                        + DATE_DESC_EXPENSE, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_EXPENSE + VALID_CATEGORY_EXPENSE + DATE_DESC_EXPENSE
                + AMOUNT_DESC_EXPENSE, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_EXPENSE + VALID_AMOUNT_EXPENSE + DATE_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_EXPENSE + VALID_AMOUNT_EXPENSE + VALID_CATEGORY_EXPENSE
                        + VALID_DATE_EXPENSE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE, Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + INVALID_DATE_DESC_FORMAT + REMARKS_DESC_EXPENSE, Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                + INVALID_DATE_DESC_FORMAT, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE
                + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        // multiple categories - not accepted
        assertParseFailure(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                        + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple dates - not accepted
        assertParseFailure(parser, NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE + CATEGORY_DESC_EXPENSE
                        + DATE_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple names - not accepted
        assertParseFailure(parser, NAME_DESC_EXPENSE + NAME_DESC_EXPENSE + AMOUNT_DESC_EXPENSE
                        + CATEGORY_DESC_EXPENSE + DATE_DESC_EXPENSE + REMARKS_DESC_EXPENSE,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
