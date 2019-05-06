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
 * Selects a recurring identified using it's displayed index from the Finance Tracker.
 */
public class SelectRecurringCommand extends Command {

    public static final String COMMAND_WORD = "selectrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the recurring identified by the index number used in the displayed recurring list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Recurring: %1$s";

    private final Index targetIndex;

    public SelectRecurringCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setSelectedRecurring(null);

        List<Recurring> filteredRecurringList = model.getFilteredRecurringList();

        if (targetIndex.getZeroBased() >= filteredRecurringList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
        }

        model.setSelectedRecurring(filteredRecurringList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectRecurringCommand // instanceof handles nulls
                && targetIndex.equals(((SelectRecurringCommand) other).targetIndex)); // state check
    }
}
