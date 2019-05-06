package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * Adds outstanding expenses from all Recurring Expenses into expenses in the Finance Tracker.
 */
public class ConvertRecurringToExpenseCommand extends Command {

    public static final String COMMAND_WORD = "convertrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "cre";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds outstanding expenses from all Recurring into "
            + "expenses in the Finance Tracker.";

    public static final String MESSAGE_CONVERT_RECURRING_SUCCESS = "All outstanding expenses from Recurring Expenses"
            + " converted into expenses successfully.\n"
            + "Number of expenses added: ";

    public ConvertRecurringToExpenseCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Recurring> lastShownRecurringList = model.getFilteredRecurringList();
        List<Recurring> deleteThese = new ArrayList<>();
        List<Recurring> addThese = new ArrayList<>();
        int numAdded = 0;

        for (int i = 0; i < lastShownRecurringList.size(); i++) {
            Recurring recurring = lastShownRecurringList.get(i);
            LocalDate lastConvertedDate = recurring.getLastConvertedDate();
            if (lastConvertedDate.isBefore(LocalDate.now())) {
                ArrayList<Expense> recurringListOfExpenses = recurring.getRecurringListOfExpenses();
                for (int j = 0; j < recurringListOfExpenses.size(); j++) {
                    LocalDate date = recurringListOfExpenses.get(j).getDate().getLocalDate();
                    if ((date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()))
                            && date.isAfter(lastConvertedDate)) {
                        model.addExpense(recurringListOfExpenses.get(j));
                        numAdded++;
                    }
                }
            }
            Recurring recurringWithUpdatedLastConvertedDate = new Recurring(recurring);
            deleteThese.add(recurring);
            addThese.add(recurringWithUpdatedLastConvertedDate);
        }
        for (int i = 0; i < deleteThese.size(); i++) {
            model.deleteRecurring(deleteThese.get(i));
        }
        for (int i = 0; i < addThese.size(); i++) {
            model.addRecurring(addThese.get(i));
        }
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_CONVERT_RECURRING_SUCCESS + numAdded + "."));
    }
}
