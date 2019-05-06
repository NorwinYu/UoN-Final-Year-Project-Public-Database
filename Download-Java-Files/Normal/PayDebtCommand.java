package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

/**
 * Converts a debt into an expense in the Finance Tracker.
 */
public class PayDebtCommand extends Command {

    public static final String COMMAND_WORD = "paydebt";

    public static final String COMMAND_WORD_SHORTCUT = "pd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Convert a debt identified by the "
            + "index number used in the displayed debt list to an expense in the Finance Tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "date]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "15-03-2019";

    public static final String MESSAGE_CONVERT_DEBT_SUCCESS = "Conversion to expense successful.\n"
            + "Debt converted: %1$s\n"
            + "New Expense added: %2$s";

    private final Index targetIndex;
    private final Date date;

    public PayDebtCommand(Index targetIndex, Date date) {
        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Debt> lastShownDebtList = model.getFilteredDebtList();

        if (targetIndex.getZeroBased() >= lastShownDebtList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
        }

        Debt debtToConvert = lastShownDebtList.get(targetIndex.getZeroBased());

        Name name = new Name("Paid Debt to " + debtToConvert.getPersonOwed().toString());
        Amount amount = debtToConvert.getAmount();
        Category category = debtToConvert.getCategory();
        String remarks = debtToConvert.getRemarks();
        Expense expense = new Expense(name, amount, date, category, remarks);

        model.deleteDebt(debtToConvert);
        model.addExpense(expense);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_CONVERT_DEBT_SUCCESS, debtToConvert, expense));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PayDebtCommand // instanceof handles nulls
                && targetIndex.equals(((PayDebtCommand) other).targetIndex)
                && date.equals(((PayDebtCommand) other).date));
    }
}
