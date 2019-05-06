package seedu.address.logic.parser.debtparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.debtcommands.EditDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditDebtCommand object
 */
public class EditDebtCommandParser implements Parser<EditDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDebtCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_DUE, PREFIX_REMARKS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDebtCommand.MESSAGE_USAGE), pe);
        }

        EditDebtCommand.EditDebtDescriptor editDebtDescriptor = new EditDebtCommand.EditDebtDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDebtDescriptor.setPersonOwed(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editDebtDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editDebtDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_DUE).isPresent()) {
            editDebtDescriptor.setDeadline(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DUE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editDebtDescriptor.setRemarks(argMultimap.getValue(PREFIX_REMARKS).get());
        }


        if (!editDebtDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDebtCommand.MESSAGE_NOT_EDITED);
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                PREFIX_DUE, PREFIX_REMARKS)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        return new EditDebtCommand(index, editDebtDescriptor);
    }

    /**
     * Returns true at least one prefix have more than to one value
     * {@code ArgumentMultiMap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }
}
