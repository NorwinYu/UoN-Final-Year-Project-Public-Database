package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_DEBT;
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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_DEBT_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_DEBT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEBT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDebts.DEBT;
import static seedu.address.testutil.TypicalDebts.DEBT_WITHOUT_REMARKS;

import org.junit.Test;

import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;
import seedu.address.testutil.DebtBuilder;

public class AddDebtCommandParserTest {
    private AddDebtCommandParser parser = new AddDebtCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Debt expectedDebt = new DebtBuilder(DEBT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_DEBT + AMOUNT_DESC_DEBT
                        + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT,
                new AddDebtCommand(expectedDebt));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT
                        + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT,
                new AddDebtCommand(expectedDebt));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no remarks
        Debt expectedDebt = new DebtBuilder(DEBT_WITHOUT_REMARKS).build();
        assertParseSuccess(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT, new AddDebtCommand(expectedDebt));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDebtCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_DEBT + VALID_CATEGORY_DEBT + DEADLINE_DESC_DEBT
                + AMOUNT_DESC_DEBT, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_DEBT + VALID_AMOUNT_DEBT + DEADLINE_DESC_DEBT
                + CATEGORY_DESC_DEBT, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + VALID_DEADLINE_DEBT
                + CATEGORY_DESC_DEBT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_DEBT + VALID_AMOUNT_DEBT + VALID_CATEGORY_DEBT
                + VALID_DEADLINE_DEBT, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_DEBT + INVALID_AMOUNT_DESC + CATEGORY_DESC_DEBT
                + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT, Amount.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + INVALID_CATEGORY_DESC
                + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT, Category.MESSAGE_CONSTRAINTS);

        // invalid date format
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + INVALID_DEADLINE_DESC_FORMAT + REMARKS_DESC_DEBT, Date.MESSAGE_CONSTRAINTS);

        // invalid date value
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + INVALID_DEADLINE_DESC_DATE + REMARKS_DESC_DEBT, Date.MESSAGE_DEADLINE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                + INVALID_DEADLINE_DESC_FORMAT, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_DEBT + AMOUNT_DESC_DEBT
                        + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDebtCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        // multiple remarks - not accepted
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                        + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT_2 + REMARKS_DESC_DEBT,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple categories - not accepted
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT_2
                        + CATEGORY_DESC_DEBT + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT,
                MESSAGE_REPEATED_PREFIX_COMMAND);

        // multiple deadlines - not accepted
        assertParseFailure(parser, NAME_DESC_DEBT + AMOUNT_DESC_DEBT + CATEGORY_DESC_DEBT
                        + DEADLINE_DESC_DEBT_2 + DEADLINE_DESC_DEBT + REMARKS_DESC_DEBT,
                MESSAGE_REPEATED_PREFIX_COMMAND);
    }
}
