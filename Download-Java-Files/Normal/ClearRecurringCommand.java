package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinanceTracker;

/**
 * Clears all recurring expenses in Finance Tracker.
 */
public class ClearRecurringCommand extends Command {

    public static final String COMMAND_WORD = "clearrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "cr";

    public static final String MESSAGE_SUCCESS = "Recurring list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReadOnlyFinanceTracker oldFt = model.getFinanceTracker();
        FinanceTracker newFt = new FinanceTracker();
        newFt.setExpenses(model.getFinanceTracker().getExpenseList());
        newFt.setBudgets(model.getFinanceTracker().getBudgetList());
        newFt.setDebts(model.getFinanceTracker().getDebtList());
        model.setFinanceTracker(newFt);
        model.commitFinanceTracker();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
