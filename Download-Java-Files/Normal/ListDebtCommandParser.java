package seedu.address.logic.parser.debtparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import seedu.address.logic.commands.debtcommands.ListDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
/**
 * Parses input arguments and creates a new ListDebtCommand object
 */
public class ListDebtCommandParser implements Parser<ListDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListDebtCommand
     * and returns an ListDebtCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListDebtCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VIEW);

        if (argMultimap.getValue(PREFIX_VIEW).isPresent()) {
            String stringView = argMultimap.getValue(PREFIX_VIEW).get();
            View view = ParserUtil.parseView(stringView);
            return new ListDebtCommand(view);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDebtCommand.MESSAGE_USAGE));
        }
    }
}
