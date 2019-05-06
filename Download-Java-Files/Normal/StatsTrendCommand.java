package seedu.address.logic.commands.statscommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;

/**
 * Calculates and displays statistics
 */
public class StatsTrendCommand extends Command {

    public static final String COMMAND_WORD = "statstrend";

    public static final String COMMAND_WORD_SHORTCUT = "stt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows statistics trends for regular periods between the Start Date and End Date. "
            + "Parameters: "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_ENDDATE + "END_DATE "
            + PREFIX_FREQUENCY + "FREQUENCY "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STARTDATE + "13-01-2000 "
            + PREFIX_ENDDATE + "13-02-2000 "
            + PREFIX_FREQUENCY + "M";

    public static final String MESSAGE_SUCCESS = "Trend Statistics Calculated!";

    private final Date startDate;
    private final Date endDate;
    private final Frequency frequency;


    public StatsTrendCommand(Date date1, Date date2, Frequency frequency) {
        requireNonNull(date1);
        requireNonNull(date2);
        requireNonNull(frequency);

        this.startDate = date1;
        this.endDate = date2;
        this.frequency = frequency;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        /* Execution */
        model.calculateStatistics("trend" , startDate, endDate, frequency);
        /* End of Execution*/

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsTrendCommand // instance of handles nulls
                && startDate.equals(((StatsTrendCommand) other).startDate)
                && endDate.equals(((StatsTrendCommand) other).endDate)
                && frequency.equals(((StatsTrendCommand) other).frequency));
    }
}
