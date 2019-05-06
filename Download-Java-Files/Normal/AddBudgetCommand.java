package seedu.address.logic.commands.budgetcommands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;

/**
 * Adds a budget to the finance tracker.
 */
public class AddBudgetCommand extends Command {

    public static final String COMMAND_WORD = "addbudget";

    public static final String COMMAND_WORD_SHORTCUT = "ab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a budget to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_STARTDATE + "START_DATE] "
            + PREFIX_ENDDATE + "END_DATE "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "

            + PREFIX_CATEGORY + "food "
            + PREFIX_AMOUNT + "300 "
            + PREFIX_STARTDATE + "01-05-2019 "
            + PREFIX_ENDDATE + "31-05-2019 "
            + PREFIX_REMARKS + "i eat too much";

    public static final String MESSAGE_SUCCESS = "New budget added:\n%1$s";

    private final Budget toAdd;

    /**
     * Creates an AddBudgetCommand to add the specified {@code budget}
     */
    public AddBudgetCommand(Budget budget) {
        requireNonNull(budget);
        if (budget.getEndDate().getLocalDate().isBefore(budget.getStartDate().getLocalDate())) {
            throw new IllegalArgumentException(Budget.MESSAGE_CONSTRAINTS_END_DATE);
        }
        toAdd = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        boolean exists = false;
        for (Budget budget:model.getFilteredBudgetList()) {
            if (budget.getCategory() == toAdd.getCategory()) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            model.addBudget(toAdd);
            model.commitFinanceTracker();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } else {
            throw new CommandException(Messages.MESSAGE_BUDGET_EXISTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
