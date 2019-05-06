package seedu.address.logic.parser.budgetparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;

/**
 * Parses input arguments and creates a new EditExpenseCommand object
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_REMARKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_AMOUNT, PREFIX_CATEGORY,
                PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_REMARKS)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        EditBudgetCommand.EditBudgetDescriptor editBudgetDescriptor = new EditBudgetCommand.EditBudgetDescriptor();
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editBudgetDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
            editBudgetDescriptor.setStartDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get()));
            editBudgetDescriptor.setIsStartDateEdited(true);
        }

        if (argMultimap.getValue(PREFIX_ENDDATE).isPresent()) {
            if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) { // already checked start date
                if (ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get()).getLocalDate()
                        .isBefore(ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get()).getLocalDate())) {
                    throw new ParseException(Budget.MESSAGE_CONSTRAINTS_END_DATE);
                }
            }
            if (!ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get()).isEqualOrAfterToday()) {
                throw new ParseException(Budget.MESSAGE_CONSTRAINTS_END_DATE_AFTER_TODAY);
            }
            editBudgetDescriptor.setEndDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editBudgetDescriptor.setRemarks(argMultimap.getValue(PREFIX_REMARKS).get());
        }

        if (!editBudgetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditBudgetCommand.MESSAGE_NOT_EDITED);
        }

        try {
            return new EditBudgetCommand(category, editBudgetDescriptor);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS_END_DATE);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true at least one prefix have more than to one value
     * {@code ArgumentMultiMap}.
     */
    private static boolean hasRepeatedPrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !(Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getAllValues(prefix).size() <= 1));
    }
}
