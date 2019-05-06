package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.debt.Debt;

/**
 * Deletes a debt identified using it's displayed index from the Finance Tracker.
 */
public class DeleteDebtCommand extends Command {

    public static final String COMMAND_WORD = "deletedebt";

    public static final String COMMAND_WORD_SHORTCUT = "dd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the debt identified by the index number used in the displayed debt list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DEBT_SUCCESS = "Deleted Debt:\n%1$s";

    private final Index targetIndex;

    public DeleteDebtCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Debt> lastShownList = model.getFilteredDebtList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
        }

        Debt debtToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDebt(debtToDelete);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_DELETE_DEBT_SUCCESS, debtToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDebtCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDebtCommand) other).targetIndex)); // state check
    }
}
