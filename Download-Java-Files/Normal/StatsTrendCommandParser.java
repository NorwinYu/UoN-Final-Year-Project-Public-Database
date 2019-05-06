package seedu.address.logic.parser.statsparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.statscommands.StatsTrendCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.statistics.Statistics;

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsTrendCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns a StatsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */

    public StatsTrendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_FREQUENCY);

        if (!arePrefixesPresent(argMultimap, PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_FREQUENCY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        }

        if (hasRepeatedPrefixes(argMultimap, PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_FREQUENCY)) {
            throw new ParseException(MESSAGE_REPEATED_PREFIX_COMMAND);
        }

        Date startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STARTDATE).get());
        Date endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ENDDATE).get());

        if (endDate.getLocalDate().isBefore(startDate.getLocalDate())) {
            throw new ParseException(Statistics.MESSAGE_CONSTRAINTS_END_DATE);
        }

        Frequency frequency = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).get());

        return new StatsTrendCommand(startDate, endDate, frequency);
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
