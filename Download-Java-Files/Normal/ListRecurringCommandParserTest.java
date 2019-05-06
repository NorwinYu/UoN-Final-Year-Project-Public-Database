package seedu.address.logic.parser.recurringparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.recurringcommands.ListRecurringCommand;
import seedu.address.model.attributes.View;

public class ListRecurringCommandParserTest {

    private static final String VALID_ARGS_ALL = " v/all";
    private static final String VALID_ARGS_MONTH = " v/month";
    private static final String VALID_ARGS_100 = " v/$100";
    private static final String VALID_ARGS_FOOD = " v/food";

    private static final String INVALID_PREFIX = " ve/others";
    private static final String INVALID_ARGS = " v/100";
    private static final String INVALID_EMPTY = "";

    private ListRecurringCommandParser parser = new ListRecurringCommandParser();

    // Test that involves all
    @Test
    public void parse_validArgsAll_returnsListRecurringCommand() {
        assertParseSuccess(parser, VALID_ARGS_ALL, new ListRecurringCommand(View.ALL));
    }

    // Test that involves Date
    @Test
    public void parse_validArgsMonth_returnsListRecurringCommand() {
        assertParseSuccess(parser, VALID_ARGS_MONTH, new ListRecurringCommand(View.MONTH));
    }

    // Test that involves Amount
    @Test
    public void parse_validArgs100_returnsListRecurringCommand() {
        assertParseSuccess(parser, VALID_ARGS_100, new ListRecurringCommand(View.$100));
    }

    // Test that involves Category
    @Test
    public void parse_validArgsFood_returnsListRecurringCommand() {
        assertParseSuccess(parser, VALID_ARGS_FOOD, new ListRecurringCommand(View.FOOD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_ARGS, View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, INVALID_PREFIX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListRecurringCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, INVALID_EMPTY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListRecurringCommand.MESSAGE_USAGE));
    }
}
