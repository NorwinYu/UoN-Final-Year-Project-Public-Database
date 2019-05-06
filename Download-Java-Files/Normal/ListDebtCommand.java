package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_1000_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_100_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_10_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_DAY_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ENT_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_FOOD_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_HEALTHCARE_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_MONTH_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_OTHERS_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_SHOPPING_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_TRANSPORT_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_TRAVEL_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_UTIL_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_WEEK_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_WORK_DEBTS;
import static seedu.address.model.Model.PREDICATE_SHOW_YEAR_DEBTS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.View;
import seedu.address.model.debt.Debt;

//@@author jamessspanggg
/**
 * Lists all debts in the Finance Tracker to the user.
 */
public class ListDebtCommand extends Command {

    public static final String COMMAND_WORD = "listdebt";

    public static final String COMMAND_WORD_SHORTCUT = "ld";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists debts in the Finance Tracker. "
            + "Parameters: "
            + PREFIX_VIEW + "VIEW\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VIEW + "food";

    public static final String MESSAGE_SUCCESS = "Listed debts: \n%1$s";

    private final View view;

    public ListDebtCommand(View view) {
        requireNonNull(view);
        this.view = view;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDebtList(this.getPredicate(this.view));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.view.getMessage()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListDebtCommand)) {
            return false;
        }

        // state check
        ListDebtCommand e = (ListDebtCommand) other;
        return view.equals(e.view);
    }

    /** Returns a {@code Predicate<Debt>} object specified by the {@code view} for filtering of debts */
    public Predicate<Debt> getPredicate(View view) {
        switch (view) {
        case ALL:
            return PREDICATE_SHOW_ALL_DEBTS;
        case DAY:
            return PREDICATE_SHOW_DAY_DEBTS;
        case WEEK:
            return PREDICATE_SHOW_WEEK_DEBTS;
        case MONTH:
            return PREDICATE_SHOW_MONTH_DEBTS;
        case YEAR:
            return PREDICATE_SHOW_YEAR_DEBTS;
        case FOOD:
            return PREDICATE_SHOW_FOOD_DEBTS;
        case WORK:
            return PREDICATE_SHOW_WORK_DEBTS;
        case TRAVEL:
            return PREDICATE_SHOW_TRAVEL_DEBTS;
        case OTHERS:
            return PREDICATE_SHOW_OTHERS_DEBTS;
        case ENTERTAINMENT:
            return PREDICATE_SHOW_ENT_DEBTS;
        case SHOPPING:
            return PREDICATE_SHOW_SHOPPING_DEBTS;
        case TRANSPORT:
            return PREDICATE_SHOW_TRANSPORT_DEBTS;
        case UTILITIES:
            return PREDICATE_SHOW_UTIL_DEBTS;
        case HEALTHCARE:
            return PREDICATE_SHOW_HEALTHCARE_DEBTS;
        case $10:
            return PREDICATE_SHOW_AMOUNT_OVER_10_DEBTS;
        case $100:
            return PREDICATE_SHOW_AMOUNT_OVER_100_DEBTS;
        case $1000:
            return PREDICATE_SHOW_AMOUNT_OVER_1000_DEBTS;
        default:
            throw new IllegalArgumentException("Invalid View.");
        }
    }
}
