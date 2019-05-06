package seedu.address.logic.parser.recurringparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECURRING;

import org.junit.Test;

import seedu.address.logic.commands.recurringcommands.SelectRecurringCommand;

/**
 * Test scope: similar to {@code DeleteRecurringCommandParserTest}.
 * @see DeleteRecurringCommandParserTest
 */
public class SelectRecurringCommandParserTest {

    private SelectRecurringCommandParser parser = new SelectRecurringCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectRecurringCommand(INDEX_FIRST_RECURRING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SelectRecurringCommand.MESSAGE_USAGE));
    }
}
