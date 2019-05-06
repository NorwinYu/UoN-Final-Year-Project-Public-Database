package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EXPENSE_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXPENSE_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DEBT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_DEBT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.debtcommands.PayDebtCommand;
import seedu.address.model.attributes.Date;

public class PayDebtCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayDebtCommand.MESSAGE_USAGE);

    private PayDebtCommandParser parser = new PayDebtCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        // date not in dd-mm-yyyy format
        assertParseFailure(parser, "1" + INVALID_DATE_DESC_FORMAT, Date.MESSAGE_CONSTRAINTS);

        // date given does not exist. e.g. 29-02-2021
        assertParseFailure(parser, "1" + INVALID_DATE_DESC_EXIST, Date.MESSAGE_DATE_DOES_NOT_EXIST);
    }

    @Test
    public void parse_dateNotSpecified_success() {
        Index targetIndex = INDEX_FIRST_DEBT;
        String userInput = targetIndex.getOneBased() + "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Date currentDate = new Date(dtf.format(LocalDate.now()));
        PayDebtCommand expectedCommand = new PayDebtCommand(targetIndex, currentDate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateSpecified_success() {
        Index targetIndex = INDEX_SECOND_DEBT;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EXPENSE;

        Date currentDate = new Date(VALID_DATE_EXPENSE);
        PayDebtCommand expectedCommand = new PayDebtCommand(targetIndex, currentDate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleDatesSpecified_acceptsLast() {
        Index targetIndex = INDEX_THIRD_DEBT;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EXPENSE + DATE_DESC_EXPENSE_2;

        Date currentDate = new Date(VALID_DATE_EXPENSE_2);
        PayDebtCommand expectedCommand = new PayDebtCommand(targetIndex, currentDate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
