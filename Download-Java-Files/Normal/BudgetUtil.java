package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand.EditBudgetDescriptor;
import seedu.address.model.budget.Budget;

/**
 * A utility class for Budget.
 */
public class BudgetUtil {
    /**
     * Returns an add budget command string for adding the {@code budget}.
     */
    public static String getAddBudgetCommand(Budget budget) {
        return AddBudgetCommand.COMMAND_WORD + " " + getBudgetDetails(budget);
    }

    /**
     * Returns the part of command string for the given {@code budget}'s details.
     */
    public static String getBudgetDetails(Budget budget) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CATEGORY + budget.getCategory().toString() + " ");
        sb.append(PREFIX_AMOUNT + budget.getAmount().toString() + " ");
        sb.append(PREFIX_STARTDATE + budget.getStartDate().toString() + " ");
        sb.append(PREFIX_ENDDATE + budget.getEndDate().toString() + " ");
        sb.append(PREFIX_REMARKS + budget.getRemarks());

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBudgetDescriptor}'s details.
     */
    public static String getEditBudgetDescriptorDetails(EditBudgetDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        descriptor.getStartDate().ifPresent(startDate -> sb.append(PREFIX_STARTDATE)
                .append(startDate.toString()).append(" "));
        descriptor.getEndDate().ifPresent(endDate -> sb.append(PREFIX_ENDDATE).append(endDate.toString()).append(" "));
        descriptor.getRemarks().ifPresent(remarks -> sb.append(PREFIX_REMARKS).append(remarks));

        return sb.toString();
    }
}
