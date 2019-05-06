package seedu.address.logic.parser.budgetparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.stream.Stream;

import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Category;

/**
 * Parses input arguments and creates a new DeleteBudgetCommand object
 */
public class DeleteBudgetCommandParser implements Parser<DeleteBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteBudgetCommand
     * and returns a DeleteBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE));
        }

        String[] toCheck = args.split("/");
        if (toCheck.length > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBudgetCommand.MESSAGE_USAGE));
        }

        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        return new DeleteBudgetCommand(category);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
