package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.SelectExpenseCommand;

/**
 * Test scope: similar to {@code DeleteExpenseCommandParserTest}.
 * @see DeleteExpenseCommandParserTest
 */
public class SelectExpenseCommandParserTest {

    private SelectExpenseCommandParser parser = new SelectExpenseCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectExpenseCommand(INDEX_FIRST_EXPENSE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SelectExpenseCommand.MESSAGE_USAGE));
    }
}
