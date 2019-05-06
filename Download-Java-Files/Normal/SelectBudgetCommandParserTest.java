package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.budgetcommands.SelectBudgetCommand;
import seedu.address.model.attributes.Category;

/**
 * Test scope: similar to {@code DeleteBudgetCommandParserTest}.
 * @see DeleteBudgetCommandParserTest
 */
public class SelectBudgetCommandParserTest {

    private SelectBudgetCommandParser parser = new SelectBudgetCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, CATEGORY_DESC_BUDGET, new SelectBudgetCommand(Category.valueOf(VALID_CATEGORY_BUDGET
                .toUpperCase())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCategory_throwsNoSuchValueException() {
        assertParseFailure(parser, INVALID_CATEGORY_DESC, String.format(Category.MESSAGE_CONSTRAINTS));
    }
}
