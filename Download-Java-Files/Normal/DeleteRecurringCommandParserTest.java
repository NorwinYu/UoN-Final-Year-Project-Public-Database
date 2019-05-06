package seedu.address.logic.parser.recurringparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECURRING;

import org.junit.Test;

import seedu.address.logic.commands.recurringcommands.DeleteRecurringCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRecurringCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRecurringCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRecurringCommandParserTest {

    private DeleteRecurringCommandParser parser = new DeleteRecurringCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteRecurringCommand(INDEX_FIRST_RECURRING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRecurringCommand.MESSAGE_USAGE));
    }
}
