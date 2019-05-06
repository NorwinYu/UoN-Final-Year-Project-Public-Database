package seedu.address.logic.commands.expensecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

//@@author jamessspanggg
/**
 * Adds an expense to the Finance Tracker.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addexpense";

    public static final String COMMAND_WORD_SHORTCUT = "ae";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Char Kuey Teow "
            + PREFIX_AMOUNT + "300 "
            + PREFIX_CATEGORY + "food "
            + PREFIX_DATE + "13-01-1996 "
            + PREFIX_REMARKS + "My lunch for tuesday";

    public static final String MESSAGE_SUCCESS = "New expense added:\n%1$s";

    private final Expense toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddExpenseCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.addExpense(toAdd);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpenseCommand // instanceof handles nulls
                && toAdd.equals(((AddExpenseCommand) other).toAdd));
    }
}
