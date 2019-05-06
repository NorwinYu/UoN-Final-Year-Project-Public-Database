package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.debtcommands.ListDebtCommand;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
public class ListDebtCommandParserTest {

    private static final String VALID_ARGS_ALL = " v/all";
    private static final String VALID_ARGS_MONTH = " v/month";
    private static final String VALID_ARGS_100 = " v/$100";
    private static final String VALID_ARGS_FOOD = " v/food";

    private static final String INVALID_PREFIX = " ve/others";
    private static final String INVALID_ARGS = " v/100";
    private static final String INVALID_EMPTY = "";

    private ListDebtCommandParser parser = new ListDebtCommandParser();

    // Test that involves all
    @Test
    public void parse_validArgsAll_returnsListDebtCommand() {
        assertParseSuccess(parser, VALID_ARGS_ALL, new ListDebtCommand(View.ALL));
    }

    // Test that involves Date
    @Test
    public void parse_validArgsMonth_returnsListDebtCommand() {
        assertParseSuccess(parser, VALID_ARGS_MONTH, new ListDebtCommand(View.MONTH));
    }

    // Test that involves Amount
    @Test
    public void parse_validArgs100_returnsListDebtCommand() {
        assertParseSuccess(parser, VALID_ARGS_100, new ListDebtCommand(View.$100));
    }

    // Test that involves Category
    @Test
    public void parse_validArgsFood_returnsListDebtCommand() {
        assertParseSuccess(parser, VALID_ARGS_FOOD, new ListDebtCommand(View.FOOD));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, INVALID_ARGS, View.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, INVALID_PREFIX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListDebtCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, INVALID_EMPTY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListDebtCommand.MESSAGE_USAGE));
    }
}
