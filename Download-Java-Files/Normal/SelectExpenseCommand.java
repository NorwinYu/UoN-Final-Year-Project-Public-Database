package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Selects a expense identified using it's displayed index from the Finance Tracker.
 */
public class SelectExpenseCommand extends Command {

    public static final String COMMAND_WORD = "selectexpense";

    public static final String COMMAND_WORD_SHORTCUT = "se";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the expense identified by the index number used in the displayed expense list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Expense: %1$s";

    private final Index targetIndex;

    public SelectExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setSelectedExpense(null);

        List<Expense> filteredExpenseList = model.getFilteredExpenseList();

        if (targetIndex.getZeroBased() >= filteredExpenseList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        model.setSelectedExpense(filteredExpenseList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectExpenseCommand // instanceof handles nulls
                && targetIndex.equals(((SelectExpenseCommand) other).targetIndex)); // state check
    }
}
