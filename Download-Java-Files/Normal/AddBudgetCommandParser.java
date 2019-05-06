package seedu.address.logic.parser.budgetparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

/**
 * Parses input arguments and creates a new AddDebtCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDebtCommand
     * and returns an AddDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_AMOUNT,
                        PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_REMARKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_ENDDATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_ENDDATE, PREFIX_STARTDATE,
                PREFIX_REMARKS)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Date startDate;
        if (argMultimap.getValue(PREFIX_STARTDATE).isPresent()) {
            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
        } else {
            // If date is not present, initialise to the current date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate currentDate = LocalDate.now();
            startDate = new Date(dtf.format(currentDate));
        }
        if (ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get()).getLocalDate()
                .isBefore(startDate.getLocalDate())) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS_END_DATE);
        }
        if (!ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get()).isEqualOrAfterToday()) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS_END_DATE_AFTER_TODAY);
        }
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get());
        String remarks;
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            remarks = argMultimap.getValue(PREFIX_REMARKS).get();
        } else {
            remarks = "";
        }

        Budget budget = new Budget(category, amount, startDate, endDate, remarks, 0, 0);

        return new AddBudgetCommand(budget);
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

