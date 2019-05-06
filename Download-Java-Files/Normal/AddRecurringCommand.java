package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.recurring.Recurring;

/**
 * Adds a recurring to the Finance Tracker.
 */
public class AddRecurringCommand extends Command {

    public static final String COMMAND_WORD = "addrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recurring to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_REMARKS + "REMARKS] "
            + PREFIX_FREQUENCY + "FREQUENCY "
            + PREFIX_OCCURRENCE + "OCCURRENCE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Phone Bill "
            + PREFIX_AMOUNT + "50 "
            + PREFIX_CATEGORY + "utilities "
            + PREFIX_DATE + "23-2-2019 "
            + PREFIX_REMARKS + "Signed a new 2 year contract "
            + PREFIX_FREQUENCY + "M "
            + PREFIX_OCCURRENCE + "24";

    public static final String MESSAGE_SUCCESS = "New recurring added:\n%1$s";

    private final Recurring toAdd;

    /**
     * Creates an AddRecurringCommand to add the specified {@code Recurring}
     */
    public AddRecurringCommand(Recurring recurring) {
        requireNonNull(recurring);
        toAdd = recurring;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.addRecurring(toAdd);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecurringCommand // instanceof handles nulls
                && toAdd.equals(((AddRecurringCommand) other).toAdd));
    }
}
