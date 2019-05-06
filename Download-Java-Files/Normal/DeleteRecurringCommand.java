package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recurring.Recurring;

/**
 * Deletes a recurring expense identified using it's displayed index from the Finance Tracker.
 */
public class DeleteRecurringCommand extends Command {

    public static final String COMMAND_WORD = "deleterecurring";

    public static final String COMMAND_WORD_SHORTCUT = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recurring identified by the index number used in the displayed recurring list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_RECURRING_SUCCESS = "Deleted Recurring:\n%1$s";

    private final Index targetIndex;

    public DeleteRecurringCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recurring> lastShownList = model.getFilteredRecurringList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
        }

        Recurring recurringToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecurring(recurringToDelete);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_DELETE_RECURRING_SUCCESS, recurringToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRecurringCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRecurringCommand) other).targetIndex)); // state check
    }
}
