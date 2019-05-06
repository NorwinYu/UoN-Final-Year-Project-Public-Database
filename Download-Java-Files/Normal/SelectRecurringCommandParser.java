package seedu.address.logic.parser.recurringparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recurringcommands.SelectRecurringCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectRecurringCommand object
 */
public class SelectRecurringCommandParser implements Parser<SelectRecurringCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectRecurringCommand
     * and returns an SelectRecurringCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectRecurringCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectRecurringCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectRecurringCommand.MESSAGE_USAGE), pe);
        }
    }
}
