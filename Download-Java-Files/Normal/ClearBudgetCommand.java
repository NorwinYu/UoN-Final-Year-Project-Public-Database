package seedu.address.logic.commands.budgetcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinanceTracker;

/**
 * Clears all budgets in Finance Tracker.
 */
public class ClearBudgetCommand extends Command {

    public static final String COMMAND_WORD = "clearbudget";

    public static final String COMMAND_WORD_SHORTCUT = "cb";

    public static final String MESSAGE_SUCCESS = "Budget list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReadOnlyFinanceTracker oldFt = model.getFinanceTracker();
        FinanceTracker newFt = new FinanceTracker();
        newFt.setExpenses(model.getFinanceTracker().getExpenseList());
        newFt.setDebts(model.getFinanceTracker().getDebtList());
        newFt.setRecurrings(model.getFinanceTracker().getRecurringList());
        model.setFinanceTracker(newFt);
        model.commitFinanceTracker();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
