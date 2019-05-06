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
 * Deletes a budget, identified using its category and its start date, from the finance tracker.
 */


public class DeleteBudgetCommand extends Command {

    public static final String COMMAND_WORD = "deletebudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by its category.\n"
            + "Parameters: CATEGORY\n"
            + "Example: " + COMMAND_WORD + " food";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted Budget.\n%1$s";

    private final int index;

    public DeleteBudgetCommand(Category targetCategory) {
        switch (targetCategory) {
        case FOOD:
            index = 0;
            break;
        case TRANSPORT:
            index = 1;
            break;
        case SHOPPING:
            index = 2;
            break;
        case WORK:
            index = 3;
            break;
        case UTILITIES:
            index = 4;
            break;
        case HEALTHCARE:
            index = 5;
            break;
        case ENTERTAINMENT:
            index = 6;
            break;
        case TRAVEL:
            index = 7;
            break;
        case OTHERS:
            index = 8;
            break;
        default:
            index = -1;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgetList();

        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_INVALID_CATEGORY);
        }

        Budget budgetToDelete = lastShownList.get(index);
        model.deleteBudget(budgetToDelete);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
    }
}
