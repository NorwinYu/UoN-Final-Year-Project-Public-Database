package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.logic.commands.expensecommands.AddExpenseCommand;
import seedu.address.logic.commands.expensecommands.EditExpenseCommand;
import seedu.address.model.expense.Expense;

/**
 * A utility class for Expense.
 */
public class ExpenseUtil {

    /**
     * Returns an add command string for adding the {@code expense}.
     */
    public static String getAddCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + expense.getName().name + " ");
        sb.append(PREFIX_AMOUNT + expense.getAmount().toString() + " ");
        sb.append(PREFIX_CATEGORY + expense.getCategory().toString() + " ");
        sb.append(PREFIX_DATE + expense.getDate().toString() + " ");
        sb.append(PREFIX_REMARKS + expense.getRemarks());

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseCommand.EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY).append(
                category.toString()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        descriptor.getRemarks().ifPresent(remarks -> sb.append(PREFIX_REMARKS).append(remarks));

        return sb.toString();
    }
}
