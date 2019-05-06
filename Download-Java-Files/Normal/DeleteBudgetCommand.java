package seedu.address.logic.commands.budgetcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;

/**
 * Deletes a budget, identified using its category and its start date, from the finance tracker.
 */


public class DeleteBudgetCommand extends Command {

    public static final String COMMAND_WORD = "deletebudget";

    public static final String COMMAND_WORD_SHORTCUT = "db";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by its category.\n"
            + "Parameters: " + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " c/food";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget.\n%1$s";

    private final Category targetCategory;

    public DeleteBudgetCommand(Category targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgetList();

        int index = -1;
        for (Budget budget : lastShownList) {
            if (budget.getCategory() == targetCategory) {
                index = lastShownList.indexOf(budget);
                break;
            }
        }

        if (index == -1) {
            throw new CommandException(MESSAGE_BUDGET_DOES_NOT_EXIST_FOR_CATEGORY);
        }

        Budget budgetToDelete = lastShownList.get(index);
        model.deleteBudget(budgetToDelete);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetCommand // instanceof handles nulls
                && targetCategory.equals(((DeleteBudgetCommand) other).targetCategory));
    }
}
