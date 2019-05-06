package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DEBT;

import org.junit.Test;

import seedu.address.logic.commands.debtcommands.SelectDebtCommand;

/**
 * Test scope: similar to {@code DeleteDebtCommandParserTest}.
 * @see DeleteDebtCommandParserTest
 */
public class SelectDebtCommandParserTest {

    private SelectDebtCommandParser parser = new SelectDebtCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectDebtCommand(INDEX_FIRST_DEBT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SelectDebtCommand.MESSAGE_USAGE));
    }
}
