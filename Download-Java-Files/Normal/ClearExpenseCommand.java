package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.budget.Budget;

/**
 * Clears all expenses in Finance Tracker.
 */
public class ClearExpenseCommand extends Command {

    public static final String COMMAND_WORD = "clearexpense";

    public static final String COMMAND_WORD_SHORTCUT = "ce";

    public static final String MESSAGE_SUCCESS = "Expense list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReadOnlyFinanceTracker oldFt = model.getFinanceTracker();
        FinanceTracker newFt = new FinanceTracker();
        newFt.setBudgets(model.getFinanceTracker().getBudgetList());
        for (Budget budget : newFt.getBudgetList()) {
            budget.setTotalSpent(0);
            budget.updatePercentage();
        }
        newFt.setDebts(model.getFinanceTracker().getDebtList());
        newFt.setRecurrings(model.getFinanceTracker().getRecurringList());
        model.setFinanceTracker(newFt);
        model.commitFinanceTracker();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
