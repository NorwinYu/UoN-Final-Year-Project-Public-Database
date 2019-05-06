package seedu.address.logic.parser.debtparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.debtcommands.PayDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Date;

/**
 * Parses input arguments and creates a new PayDebtCommand object
 */
public class PayDebtCommandParser implements Parser<PayDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PayDebtCommand
     * and returns an PayDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PayDebtCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayDebtCommand.MESSAGE_USAGE), pe);
        }

        Date date;
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            // If date is not present, initialise to the current date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate currentDate = LocalDate.now();
            date = new Date(dtf.format(currentDate));
        }

        return new PayDebtCommand(index, date);
    }
}
