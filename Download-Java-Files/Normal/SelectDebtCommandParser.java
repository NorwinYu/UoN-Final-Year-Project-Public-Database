package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.debtcommands.SelectDebtCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectDebtCommand object
 */
public class SelectDebtCommandParser implements Parser<SelectDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectDebtCommand
     * and returns an SelectDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectDebtCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectDebtCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDebtCommand.MESSAGE_USAGE), pe);
        }
    }
}
