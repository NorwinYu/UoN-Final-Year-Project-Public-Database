package seedu.address.logic.parser.recurringparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import seedu.address.logic.commands.recurringcommands.ListRecurringCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
/**
 * Parses input arguments and creates a new ListRecurringCommand object
 */
public class ListRecurringCommandParser implements Parser<ListRecurringCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListRecurringCommand
     * and returns an ListRecurringCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListRecurringCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VIEW);

        if (argMultimap.getValue(PREFIX_VIEW).isPresent()) {
            String stringView = argMultimap.getValue(PREFIX_VIEW).get();
            View view = ParserUtil.parseView(stringView);
            return new ListRecurringCommand(view);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRecurringCommand.MESSAGE_USAGE));
        }
    }
}
