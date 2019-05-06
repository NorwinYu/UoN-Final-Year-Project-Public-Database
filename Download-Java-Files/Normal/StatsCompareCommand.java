package seedu.address.logic.commands.statscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE2;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;

/**
 * Calculates and displays statistics
 */
public class StatsCompareCommand extends Command {

    public static final String COMMAND_WORD = "statscompare";

    public static final String COMMAND_WORD_SHORTCUT = "stc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Compares statistics between two time ranges. "
            + "Parameters: "
            + PREFIX_STARTDATE1 + "START_DATE_1 "
            + PREFIX_STARTDATE2 + "START_DATE_2 "
            + PREFIX_FREQUENCY + "FREQUENCY "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STARTDATE1 + "13-01-2000 "
            + PREFIX_STARTDATE2 + "13-02-2000 "
            + PREFIX_FREQUENCY + "M";

    public static final String MESSAGE_SUCCESS = "Comparison Statistics Calculated!";

    private final Date startDate1;
    private final Date startDate2;
    private final Frequency frequency;


    public StatsCompareCommand(Date date1, Date date2, Frequency frequency) {
        requireNonNull(date1);
        requireNonNull(date2);
        requireNonNull(frequency);

        this.startDate1 = date1;
        this.startDate2 = date2;
        this.frequency = frequency;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        /* Execution */
        model.calculateStatistics("compare" , startDate1, startDate2, frequency);
        /* End of Execution*/

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCompareCommand // instance of handles nulls
                && startDate1.equals(((StatsCompareCommand) other).startDate1)
                && startDate2.equals(((StatsCompareCommand) other).startDate2)
                && frequency.equals(((StatsCompareCommand) other).frequency));
    }
}
