package seedu.address.logic.commands.statscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.Date;

/**
 * Calculates and displays statistics
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String COMMAND_WORD_SHORTCUT = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates statistics between the Start Date and End Date "
            + "Parameters: "
            + "[" + PREFIX_STARTDATE + "START_DATE] "
            + "[" + PREFIX_ENDDATE + "END_DATE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STARTDATE + "13-01-2000 "
            + PREFIX_ENDDATE + "13-02-2000 ";

    public static final String MESSAGE_SUCCESS = "Statistics Calculated!";

    private final Date startDate;
    private final Date endDate;


    public StatsCommand(Date startDate, Date endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        /* Execution */
        model.calculateStatistics("stats" , startDate, endDate, null);
        /* End of Execution*/

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCommand // instance of handles nulls
                && startDate.equals(((StatsCommand) other).startDate)
                && endDate.equals(((StatsCommand) other).endDate));
    }
}
