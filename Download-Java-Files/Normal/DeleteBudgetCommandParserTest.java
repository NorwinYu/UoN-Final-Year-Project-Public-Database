package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.model.attributes.Category;

public class DeleteBudgetCommandParserTest {
    private DeleteBudgetCommandParser parser = new DeleteBudgetCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, CATEGORY_DESC_BUDGET, new DeleteBudgetCommand(Category.valueOf(VALID_CATEGORY_BUDGET
                .toUpperCase())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCategory_throwsNoSuchValueException() {
        assertParseFailure(parser, INVALID_CATEGORY_DESC, String.format(Category.MESSAGE_CONSTRAINTS));
    }
}
