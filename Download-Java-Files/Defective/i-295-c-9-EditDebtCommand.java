package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEBTS;

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
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;


/**
 * Edits the details of an existing debt in the Finance Tracker.
 */
public class EditDebtCommand extends Command {

    public static final String COMMAND_WORD = "editdebt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the debt identified "
            + "by the index number used in the displayed debt list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "PERSON_OWED] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DUE + "DEADLINE] "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Hatyai "
            + PREFIX_AMOUNT + "400 "
            + PREFIX_CATEGORY + "travel"
            + PREFIX_DUE + "21-02-2019";

    public static final String MESSAGE_EDIT_DEBT_SUCCESS = "Edited debt:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditDebtDescriptor editDebtDescriptor;

    /**
     * @param index of the expense in the filtered debt list to edit
     * @param editDebtDescriptor details to edit the debt with
     */
    public EditDebtCommand(Index index, EditDebtDescriptor editDebtDescriptor) {
        requireNonNull(index);
        requireNonNull(editDebtDescriptor);

        this.index = index;
        this.editDebtDescriptor = new EditDebtDescriptor(editDebtDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Debt> lastShownList = model.getFilteredDebtList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEBT_DISPLAYED_INDEX);
        }

        Debt debtToEdit = lastShownList.get(index.getZeroBased());
        Debt editeddebt = createEditedDebt(debtToEdit, editDebtDescriptor);

        model.setDebt(debtToEdit, editeddebt);
        model.updateFilteredDebtList(PREDICATE_SHOW_ALL_DEBTS);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_EDIT_DEBT_SUCCESS, editeddebt));
    }

    /**
     * Creates and returns a {@code Debt} with the details of {@code debtToEdit}
     * edited with {@code editDebtDescriptor}.
     */
    private static Debt createEditedDebt(Debt debtToEdit, EditDebtDescriptor editDebtDescriptor) {
        assert debtToEdit != null;

        Name updatedName = editDebtDescriptor.getPersonOwed().orElse(debtToEdit.getPersonOwed());
        Amount updatedAmount = editDebtDescriptor.getAmount().orElse(debtToEdit.getAmount());
        Category updatedCategory = editDebtDescriptor.getCategory().orElse(debtToEdit.getCategory());
        Date updatedDate = editDebtDescriptor.getDeadline().orElse(debtToEdit.getDeadline());
        String updatedRemarks = editDebtDescriptor.getRemarks().orElse(debtToEdit.getRemarks());

        return new Debt(updatedName, updatedAmount, updatedDate, updatedCategory, updatedRemarks);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDebtCommand)) {
            return false;
        }

        // state check
        EditDebtCommand e = (EditDebtCommand) other;
        return index.equals(e.index)
                && editDebtDescriptor.equals(e.editDebtDescriptor);
    }

    /**
     * Stores the details to edit the debt with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditDebtDescriptor {
        private Name personOwed;
        private Amount amount;
        private Date deadline;
        private Category category;
        private String remarks;

        public EditDebtDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDebtDescriptor(EditDebtDescriptor toCopy) {
            setPersonOwed(toCopy.personOwed);
            setAmount(toCopy.amount);
            setDeadline(toCopy.deadline);
            setCategory(toCopy.category);
            setRemarks(toCopy.remarks);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(personOwed, amount, deadline, category, remarks);
        }

        public void setPersonOwed(Name personOwed) {
            this.personOwed = personOwed;
        }

        public Optional<Name> getPersonOwed() {
            return Optional.ofNullable(personOwed);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
        }

        public Optional<Date> getDeadline() {
            return Optional.ofNullable(deadline);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDebtDescriptor)) {
                return false;
            }

            // state check
            EditDebtDescriptor e = (EditDebtDescriptor) other;

            return getPersonOwed().equals(e.getPersonOwed())
                    && getAmount().equals(e.getAmount())
                    && getDeadline().equals(e.getDeadline())
                    && getCategory().equals(e.getCategory())
                    && getRemarks().equals(e.getRemarks());
        }
    }
}
