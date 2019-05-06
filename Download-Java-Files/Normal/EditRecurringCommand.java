package seedu.address.logic.commands.recurringcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECURRING;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;

/**
 * Edits the details of an existing recurring in the Finance Tracker.
 */
public class EditRecurringCommand extends Command {

    public static final String COMMAND_WORD = "editrecurring";

    public static final String COMMAND_WORD_SHORTCUT = "er";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recurring identified "
            + "by the index number used in the displayed recurring list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_REMARKS + "REMARKS] "
            + "[" + PREFIX_FREQUENCY + "FREQUENCY] "
            + "[" + PREFIX_OCCURRENCE + "OCCURRENCE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Phone Bill Latest "
            + PREFIX_AMOUNT + "51 ";

    public static final String MESSAGE_EDIT_RECURRING_SUCCESS = "Edited Recurring:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditRecurringDescriptor editRecurringDescriptor;

    /**
     * @param index of the recurring in the filtered recurring list to edit
     * @param editRecurringDescriptor details to edit the recurring with
     */
    public EditRecurringCommand(Index index, EditRecurringDescriptor editRecurringDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecurringDescriptor);

        this.index = index;
        this.editRecurringDescriptor = new EditRecurringDescriptor(editRecurringDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recurring> lastShownList = model.getFilteredRecurringList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECURRING_DISPLAYED_INDEX);
        }

        Recurring recurringToEdit = lastShownList.get(index.getZeroBased());
        Recurring editedRecurring = createEditedRecurring(recurringToEdit, editRecurringDescriptor);

        model.setRecurring(recurringToEdit, editedRecurring);
        model.updateFilteredRecurringList(PREDICATE_SHOW_ALL_RECURRING);

        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_EDIT_RECURRING_SUCCESS, editedRecurring));
    }

    /**
     * Creates and returns a {@code Recurring} with the details of {@code recurringToEdit}
     * edited with {@code editRecurringDescriptor}.
     */
    private static Recurring createEditedRecurring(Recurring recurringToEdit,
                                                   EditRecurringDescriptor editRecurringDescriptor) {
        assert recurringToEdit != null;

        LocalDate lastConvertedDate = recurringToEdit.getLastConvertedDate();

        Name updatedName = editRecurringDescriptor.getName().orElse(recurringToEdit.getName());
        Amount updatedAmount = editRecurringDescriptor.getAmount().orElse(recurringToEdit.getAmount());
        Category updatedCategory = editRecurringDescriptor.getCategory().orElse(recurringToEdit.getCategory());
        Date updatedDate = editRecurringDescriptor.getDate().orElse(recurringToEdit.getDate());
        String updatedRemarks = editRecurringDescriptor.getRemarks().orElse(recurringToEdit.getRemarks());
        Frequency updatedFrequency = editRecurringDescriptor.getFrequency().orElse(recurringToEdit.getFrequency());
        Occurrence updatedOccurrence = editRecurringDescriptor.getOccurrence().orElse(recurringToEdit.getOccurrence());

        return new Recurring(updatedName, updatedAmount, updatedDate, updatedCategory, updatedRemarks,
                updatedFrequency, updatedOccurrence, lastConvertedDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecurringCommand)) {
            return false;
        }

        // state check
        EditRecurringCommand e = (EditRecurringCommand) other;
        return index.equals(e.index)
                && editRecurringDescriptor.equals(e.editRecurringDescriptor);
    }

    /**
     * Stores the details to edit the recurring with. Each non-empty field value will replace the
     * corresponding field value of the recurring.
     */
    public static class EditRecurringDescriptor {
        private Name name;
        private Amount amount;
        private Date date;
        private Category category;
        private String remarks;
        private Frequency frequency;
        private Occurrence occurrence;

        public EditRecurringDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecurringDescriptor(EditRecurringDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategory(toCopy.category);
            setRemarks(toCopy.remarks);
            setFrequency(toCopy.frequency);
            setOccurrence(toCopy.occurrence);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, date, category, remarks, frequency, occurrence);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {

            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Optional<String> getRemarks() {
            return Optional.ofNullable(remarks);
        }

        public void setFrequency(Frequency frequency) {
            this.frequency = frequency;
        }

        public Optional<Frequency> getFrequency() {
            return Optional.ofNullable(frequency);
        }

        public void setOccurrence(Occurrence occurrence) {
            this.occurrence = occurrence;
        }

        public Optional<Occurrence> getOccurrence() {
            return Optional.ofNullable(occurrence);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecurringDescriptor)) {
                return false;
            }

            // state check
            EditRecurringDescriptor e = (EditRecurringDescriptor) other;

            return getName().equals(e.getName())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getCategory().equals(e.getCategory())
                    && getRemarks().equals(e.getRemarks())
                    && getFrequency().equals(e.getFrequency())
                    && (getOccurrence().equals(e.getOccurrence()));
        }
    }
}
