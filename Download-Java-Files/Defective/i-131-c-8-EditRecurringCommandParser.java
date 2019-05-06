package seedu.address.logic.parser.recurringparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecurringCommand object
 */
public class EditRecurringCommandParser implements Parser<EditRecurringCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecurringCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_DATE, PREFIX_REMARKS, PREFIX_FREQUENCY, PREFIX_OCCURRENCE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecurringCommand.MESSAGE_USAGE), pe);
        }

        EditRecurringCommand.EditRecurringDescriptor editRecurringDescriptor =
                new EditRecurringCommand.EditRecurringDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecurringDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editRecurringDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editRecurringDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editRecurringDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editRecurringDescriptor.setRemarks(argMultimap.getValue(PREFIX_REMARKS).get());
        }


        if (!editRecurringDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecurringCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecurringCommand(index, editRecurringDescriptor);
    }
}

