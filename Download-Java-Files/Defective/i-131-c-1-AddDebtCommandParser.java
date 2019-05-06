package seedu.address.logic.parser.debtparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.util.stream.Stream;

import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;

/**
 * Parses input arguments and creates a new AddDebtCommand object
 */
public class AddDebtCommandParser implements Parser<AddDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDebtCommand
     * and returns an AddDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDebtCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_CATEGORY,
                        PREFIX_DUE, PREFIX_REMARKS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DUE, PREFIX_AMOUNT, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDebtCommand.MESSAGE_USAGE));
        }

        Name personOwed = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Date deadline = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DUE).get());
        String remarks = null;
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            remarks = argMultimap.getValue(PREFIX_REMARKS).get();
        }

        Debt debt = new Debt(personOwed, amount, deadline, category, remarks);

        return new AddDebtCommand(debt);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
