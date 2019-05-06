package seedu.address.logic.commands.budgetcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;

/**
 * Selects a expense identified using it's displayed index from the Finance Tracker.
 */
public class SelectBudgetCommand extends Command {

    public static final String COMMAND_WORD = "selectbudget";

    public static final String COMMAND_WORD_SHORTCUT = "sb";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the budget identified by the category.\n"
            + "Parameters: c/CATEGORY\n"
            + "Example: " + COMMAND_WORD + " c/food";

    public static final String MESSAGE_SELECT_BUDGET_SUCCESS = "Selected Budget: %1$s";

    private final Category targetCategory;

    public SelectBudgetCommand(Category targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setSelectedBudget(null);

        List<Budget> filteredBudgetList = model.getFilteredBudgetList();

        int index = -1;
        for (Budget budget : filteredBudgetList) {
            if (budget.getCategory() == targetCategory) {
                index = filteredBudgetList.indexOf(budget);
            }
        }

        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);
        }

        model.setSelectedBudget(filteredBudgetList.get(index));
        return new CommandResult(String.format(MESSAGE_SELECT_BUDGET_SUCCESS, targetCategory));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectBudgetCommand // instanceof handles nulls
                && targetCategory.equals(((SelectBudgetCommand) other).targetCategory)); // state check
    }
}
