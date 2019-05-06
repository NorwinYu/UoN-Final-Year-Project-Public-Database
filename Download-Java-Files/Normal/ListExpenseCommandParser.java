package seedu.address.logic.parser.expenseparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import seedu.address.logic.commands.expensecommands.ListExpenseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.View;

//@@author jamessspanggg
/**
 * Parses input arguments and creates a new ListExpenseCommand object
 */
public class ListExpenseCommandParser implements Parser<ListExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListExpenseCommand
     * and returns an ListExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VIEW);

        if (argMultimap.getValue(PREFIX_VIEW).isPresent()) {
            String stringView = argMultimap.getValue(PREFIX_VIEW).get();
            View view = ParserUtil.parseView(stringView);
            return new ListExpenseCommand(view);

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListExpenseCommand.MESSAGE_USAGE));
        }
    }
}
