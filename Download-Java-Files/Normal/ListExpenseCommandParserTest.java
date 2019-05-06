package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.expensecommands.ListExpenseCommand;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
public class ListExpenseCommandParserTest {

    private static final String VALID_ARGS_ALL = " v/all";
    private static final String VALID_ARGS_MONTH = " v/month";
    private static final String VALID_ARGS_100 = " v/$100";
    private static final String VALID_ARGS_FOOD = " v/food";

    private static final String INVALID_PREFIX = " ve/all";
    private static final String INVALID_ARGS = " v/alla";
    private static final String INVALID_EMPTY = "";

    private ListExpenseCommandParser parser = new ListExpenseCommandParser();

    // Test that involves all
    @Test
    public void parse_validArgsAll_returnsListExpenseCommand() {
        assertParseSuccess(parser, VALID_ARGS_ALL, new ListExpenseCommand(View.ALL));
    }

    // Test that involves Date
    @Test
    public void parse_validArgsMonth_returnsListExpenseCommand() {
        assertParseSuccess(parser, VALID_ARGS_MONTH, new ListExpenseCommand(View.MONTH));
    }

    // Test that involves Amount
    @Test
    public void parse_validArgs100_returnsListExpenseCommand() {
        assertParseSuccess(parser, VALID_ARGS_100, new ListExpenseCommand(View.$100));
    }

    // Test that involves Category
    @Test
    public void parse_validArgsFood_returnsListExpenseCommand() {
        assertParseSuccess(parser, VALID_ARGS_FOOD, new ListExpenseCommand(View.FOOD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_ARGS, View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, INVALID_PREFIX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, INVALID_EMPTY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpenseCommand.MESSAGE_USAGE));
    }
}
