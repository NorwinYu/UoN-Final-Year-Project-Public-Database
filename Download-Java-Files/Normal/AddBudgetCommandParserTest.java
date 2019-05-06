package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.ENDDATE_DESC_BUDGET_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_BUDGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBudgets.BUDGET;
import static seedu.address.testutil.TypicalBudgets.BUDGET_WITHOUT_REMARKS;

import org.junit.Test;

import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;
import seedu.address.testutil.BudgetBuilder;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Budget expectedBudget = new BudgetBuilder(BUDGET).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                        + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET,
                new AddBudgetCommand(expectedBudget));

        assertParseSuccess(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                        + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET,
                new AddBudgetCommand(expectedBudget));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        //Budget expectedBudget = new BudgetBuilder(BUDGET_WITH_TODAYS_DATE).build();
        //assertParseSuccess(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + ENDDATE_DESC_BUDGET
        //+ REMARKS_DESC_BUDGET, new AddBudgetCommand(expectedBudget));

        Budget expectedBudget = new BudgetBuilder(BUDGET_WITHOUT_REMARKS).build();
        assertParseSuccess(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET, new AddBudgetCommand(expectedBudget));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);

        // missing category prefix
        assertParseFailure(parser, VALID_CATEGORY_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + VALID_AMOUNT_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, expectedMessage);

        // missing end date prefix
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + VALID_ENDDATE_BUDGET + REMARKS_DESC_BUDGET, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CATEGORY_BUDGET + VALID_AMOUNT_BUDGET + VALID_STARTDATE_BUDGET
                + VALID_ENDDATE_BUDGET + VALID_REMARKS_BUDGET, expectedMessage);
    }

    // pass
    @Test
    public void parse_invalidValue_failure() {
        // invalid category
        assertParseFailure(parser, INVALID_CATEGORY_DESC + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, Category.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_AMOUNT_DESC + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, Amount.MESSAGE_CONSTRAINTS);

        // invalid start date format
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + INVALID_STARTDATE_DESC_FORMAT
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, Date.MESSAGE_CONSTRAINTS);

        // invalid end date format
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + INVALID_ENDDATE_DESC_FORMAT + REMARKS_DESC_BUDGET, Date.MESSAGE_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + INVALID_STARTDATE_DESC_EXIST
                + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET, Date.MESSAGE_DATE_DOES_NOT_EXIST);

        // invalid end date
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + INVALID_ENDDATE_DESC_EXIST + REMARKS_DESC_BUDGET, Date.MESSAGE_DATE_DOES_NOT_EXIST);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + INVALID_AMOUNT_DESC + STARTDATE_DESC_BUDGET
                + INVALID_ENDDATE_DESC_EXIST + REMARKS_DESC_BUDGET, Amount.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                        + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET + REMARKS_DESC_BUDGET,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));

        // end date before start date
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                + ENDDATE_DESC_BUDGET_2 + REMARKS_DESC_BUDGET, Budget.MESSAGE_CONSTRAINTS_END_DATE);

    }

    @Test
    public void parse_repeatedPrefix_failure() {
        // multiple remarks - not accepted
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                        + ENDDATE_DESC_BUDGET_2 + REMARKS_DESC_BUDGET + REMARKS_DESC_DEBT_2,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple categories - not accepted
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET
                        + STARTDATE_DESC_BUDGET + ENDDATE_DESC_BUDGET_2 + REMARKS_DESC_BUDGET,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple end dates - not accepted
        assertParseFailure(parser, CATEGORY_DESC_BUDGET + AMOUNT_DESC_BUDGET + STARTDATE_DESC_BUDGET
                        + ENDDATE_DESC_BUDGET_2 + ENDDATE_DESC_BUDGET + REMARKS_DESC_DEBT_2,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
