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
 * Selects a debt identified using it's displayed index from the Finance Tracker.
 */
public class SelectDebtCommand extends Command {

    public static final String COMMAND_WORD = "selectdebt";

    public static final String COMMAND_WORD_SHORTCUT = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the debt identified by the index number used in the displayed debt list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_DEBT_SUCCESS = "Selected Debt: %1$s";

    private final Index targetIndex;

    public SelectDebtCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setSelectedDebt(null);

        List<Debt> filteredDebtList = model.getFilteredDebtList();

        if (targetIndex.getZeroBased() >= filteredDebtList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
        }

        model.setSelectedDebt(filteredDebtList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_DEBT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDebtCommand // instanceof handles nulls
                && targetIndex.equals(((SelectDebtCommand) other).targetIndex)); // state check
    }
}
