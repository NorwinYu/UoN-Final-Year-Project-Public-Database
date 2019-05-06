package seedu.address.logic.parser.expenseparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expensecommands.EditExpenseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditExpenseCommand object
 */
public class EditExpenseCommandParser implements Parser<EditExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_DATE, PREFIX_REMARKS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE),
                    pe);
        }

        EditExpenseCommand.EditExpenseDescriptor editRecurringDescriptor =
                new EditExpenseCommand.EditExpenseDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecurringDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editRecurringDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editRecurringDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editRecurringDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editRecurringDescriptor.setRemarks(argMultimap.getValue(PREFIX_REMARKS).get());
        }


        if (!editRecurringDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenseCommand.MESSAGE_NOT_EDITED);
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                PREFIX_DATE, PREFIX_REMARKS)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        return new EditExpenseCommand(index, editRecurringDescriptor);
    }

    /**
     * Returns true at least one prefix have more than to one value
     * {@code ArgumentMultiMap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }
}
