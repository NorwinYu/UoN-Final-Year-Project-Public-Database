package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_1000_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_100_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_AMOUNT_OVER_10_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_DAY_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_ENT_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_FOOD_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_HEALTHCARE_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_MONTH_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_OTHERS_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_SHOPPING_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_TRANSPORT_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_TRAVEL_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_UTIL_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_WEEK_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_WORK_RECURRING;
import static seedu.address.model.Model.PREDICATE_SHOW_YEAR_RECURRING;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.attributes.View;
import seedu.address.model.recurring.Recurring;

//@@author jamessspanggg
/**
 * Lists all recurring in the Finance Tracker to the user.
 */
public class ListRecurringCommand extends Command {

    public static final String COMMAND_WORD = "listrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "lr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists recurrings in the Finance Tracker. "
            + "Parameters: "
            + PREFIX_VIEW + "VIEW\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VIEW + "food";

    public static final String MESSAGE_SUCCESS = "Listed recurrings:\n%1$s";

    private final View view;

    public ListRecurringCommand(View view) {
        requireNonNull(view);
        this.view = view;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        requireNonNull(model);
        model.updateFilteredRecurringList(this.getPredicate(this.view));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.view.getMessage()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListRecurringCommand)) {
            return false;
        }

        // state check
        ListRecurringCommand e = (ListRecurringCommand) other;
        return view.equals(e.view);
    }

    /** Returns a {@code Predicate<Recurring>} object specified by the {@code view} for filtering of recurrings */
    private Predicate<Recurring> getPredicate(View view) {
        switch (view) {
        case ALL:
            return PREDICATE_SHOW_ALL_RECURRING;
        case DAY:
            return PREDICATE_SHOW_DAY_RECURRING;
        case WEEK:
            return PREDICATE_SHOW_WEEK_RECURRING;
        case MONTH:
            return PREDICATE_SHOW_MONTH_RECURRING;
        case YEAR:
            return PREDICATE_SHOW_YEAR_RECURRING;
        case FOOD:
            return PREDICATE_SHOW_FOOD_RECURRING;
        case WORK:
            return PREDICATE_SHOW_WORK_RECURRING;
        case TRAVEL:
            return PREDICATE_SHOW_TRAVEL_RECURRING;
        case OTHERS:
            return PREDICATE_SHOW_OTHERS_RECURRING;
        case ENTERTAINMENT:
            return PREDICATE_SHOW_ENT_RECURRING;
        case SHOPPING:
            return PREDICATE_SHOW_SHOPPING_RECURRING;
        case TRANSPORT:
            return PREDICATE_SHOW_TRANSPORT_RECURRING;
        case UTILITIES:
            return PREDICATE_SHOW_UTIL_RECURRING;
        case HEALTHCARE:
            return PREDICATE_SHOW_HEALTHCARE_RECURRING;
        case $10:
            return PREDICATE_SHOW_AMOUNT_OVER_10_RECURRING;
        case $100:
            return PREDICATE_SHOW_AMOUNT_OVER_100_RECURRING;
        case $1000:
            return PREDICATE_SHOW_AMOUNT_OVER_1000_RECURRING;
        default:
            throw new IllegalArgumentException("Invalid View.");
        }
    }
}
