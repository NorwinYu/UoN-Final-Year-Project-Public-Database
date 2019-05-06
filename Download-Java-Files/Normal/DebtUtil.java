package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.logic.commands.debtcommands.EditDebtCommand.EditDebtDescriptor;
import seedu.address.model.debt.Debt;


/**
 * A utility class for Debt.
 */
public class DebtUtil {

    /**
     * Returns an add debt command string for adding the {@code debt}.
     */
    public static String getAddDebtCommand(Debt debt) {
        return AddDebtCommand.COMMAND_WORD + " " + getDebtDetails(debt);
    }

    /**
     * Returns the part of command string for the given {@code debt}'s details.
     */
    public static String getDebtDetails(Debt debt) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + debt.getPersonOwed().name + " ");
        sb.append(PREFIX_AMOUNT + debt.getAmount().toString() + " ");
        sb.append(PREFIX_CATEGORY + debt.getCategory().toString() + " ");
        sb.append(PREFIX_DUE + debt.getDeadline().toString() + " ");
        sb.append(PREFIX_REMARKS + debt.getRemarks());

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDebtDescriptor}'s details.
     */
    public static String getEditDebtDescriptorDetails(EditDebtDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPersonOwed().ifPresent(personOwed -> sb.append(PREFIX_NAME).append(personOwed.name).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.toString()).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY).append(
                category.toString()).append(" "));
        descriptor.getDeadline().ifPresent(deadline -> sb.append(PREFIX_DUE).append(deadline.toString()).append(" "));
        descriptor.getRemarks().ifPresent(remarks -> sb.append(PREFIX_REMARKS).append(remarks));

        return sb.toString();
    }
}
