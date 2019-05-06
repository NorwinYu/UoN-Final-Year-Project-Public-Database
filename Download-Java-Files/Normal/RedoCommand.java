package seedu.address.logic.commands.generalcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECURRING;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s Finance Tracker to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoFinanceTracker()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoFinanceTracker();
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        model.updateFilteredDebtList(PREDICATE_SHOW_ALL_DEBTS);
        model.updateFilteredRecurringList(PREDICATE_SHOW_ALL_RECURRING);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
