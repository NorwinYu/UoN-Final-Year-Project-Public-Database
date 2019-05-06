package seedu.address.logic.parser.expenseparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.SelectExpenseCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectExpenseCommand object
 */
public class SelectExpenseCommandParser implements Parser<SelectExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectExpenseCommand
     * and returns an SelectExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectExpenseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectExpenseCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectExpenseCommand.MESSAGE_USAGE), pe);
        }
    }
}
