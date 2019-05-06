package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import seedu.address.model.Model;
import seedu.address.model.debt.Debt;


/**
 * Adds a debt to the Finance Tracker.
 */
public class AddDebtCommand extends Command {

    public static final String COMMAND_WORD = "adddebt";

    public static final String COMMAND_WORD_SHORTCUT = "ad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a debt to the Finance Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "PERSON_OWED "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DUE + "DEADLINE "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_AMOUNT + "500 "
            + PREFIX_CATEGORY + "travel "
            + PREFIX_DUE + "15-06-2019 "
            + PREFIX_REMARKS + "japan air ticket";

    public static final String MESSAGE_SUCCESS = "New debt added:\n%1$s";

    private final Debt toAdd;

    /**
     * Creates an AddDebtCommand to add the specified {@code Debt}
     */
    public AddDebtCommand(Debt debt) {
        requireNonNull(debt);
        toAdd = debt;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        System.out.println("got here (A)");
        model.addDebt(toAdd);
        System.out.println("got here (B)");
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDebtCommand // instanceof handles nulls
                && toAdd.equals(((AddDebtCommand) other).toAdd));
    }
}
