package seedu.address.model.recurring;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.expense.Expense;

/**
 * Represents a Recurring in the finance tracker.
 * Guarantees: field values are validated, immutable.
 */
public class Recurring extends Expense {
    // Additional fields
    private Frequency frequency;
    private Occurrence occurrence;
    private LocalDate lastConvertedDate;
    private ArrayList<Expense> recurringListOfExpenses;

    /**
     * Initializes a newly created Recurring object that contains only the compulsory fields.
     */
    public Recurring(Name name, Amount amount, Date date, Category category, String remarks, Frequency frequency,
                     Occurrence occurrence) {
        super(name, amount, date, category, remarks);
        requireAllNonNull(frequency, occurrence);
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.lastConvertedDate = date.getLocalDate().minusDays(1);
        this.recurringListOfExpenses = new ArrayList<>(occurrence.value);
        for (int i = 0; i < occurrence.value; i++) {
            Name newName = new Name(name.name + " (Recurring)");

            Date newDate = new Date(date);
            if (frequency.value.equals("D")) {
                newDate.setLocalDate(date.getLocalDate().plusDays(i));
            } else if (frequency.value.equals("M")) {
                newDate.setLocalDate(date.getLocalDate().plusMonths(i));
            } else if (frequency.value.equals("Y")) {
                newDate.setLocalDate(date.getLocalDate().plusYears(i));
            }

            Expense toAdd = new Expense(newName, amount, newDate, category, remarks);
            //System.out.println(toAdd);
            this.recurringListOfExpenses.add(toAdd);
        }
    }

    /**
     * Initializes a newly created Recurring object that contains the compulsory fields and the lastConvertedDate.
     */
    public Recurring(Name name, Amount amount, Date date, Category category, String remarks, Frequency frequency,
                     Occurrence occurrence, LocalDate lastConvertedDate) {
        super(name, amount, date, category, remarks);
        requireAllNonNull(frequency, occurrence);
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.lastConvertedDate = lastConvertedDate;
        this.recurringListOfExpenses = new ArrayList<>(occurrence.value);
        for (int i = 0; i < occurrence.value; i++) {
            Name newName = new Name(name.name + " (Recurring)");

            Date newDate = new Date(date);
            if (frequency.value.equals("D")) {
                newDate.setLocalDate(date.getLocalDate().plusDays(i));
            } else if (frequency.value.equals("M")) {
                newDate.setLocalDate(date.getLocalDate().plusMonths(i));
            } else if (frequency.value.equals("Y")) {
                newDate.setLocalDate(date.getLocalDate().plusYears(i));
            }

            Expense toAdd = new Expense(newName, amount, newDate, category, remarks);
            //System.out.println(toAdd);
            this.recurringListOfExpenses.add(toAdd);
        }
    }

    /**
     * Initializes a Recurring object from Jackson.
     */
    public Recurring(Name name, Amount amount, Date date, Category category, String remarks, Frequency frequency,
                     Occurrence occurrence, String localDate) {
        super(name, amount, date, category, remarks);
        requireAllNonNull(frequency, occurrence);
        this.frequency = frequency;
        this.occurrence = occurrence;
        this.lastConvertedDate = LocalDate.parse(localDate);
        this.recurringListOfExpenses = new ArrayList<>(occurrence.value);
        for (int i = 0; i < occurrence.value; i++) {
            Name newName = new Name(name.name + " (Recurring)");

            Date newDate = new Date(date);
            if (frequency.value.equals("D")) {
                newDate.setLocalDate(date.getLocalDate().plusDays(i));
            } else if (frequency.value.equals("M")) {
                newDate.setLocalDate(date.getLocalDate().plusMonths(i));
            } else if (frequency.value.equals("Y")) {
                newDate.setLocalDate(date.getLocalDate().plusYears(i));
            }

            Expense toAdd = new Expense(newName, amount, newDate, category, remarks);
            //System.out.println(toAdd);
            this.recurringListOfExpenses.add(toAdd);
        }
    }

    /**
     * Initializes a newly created Recurring object from another Recurring object.
     */
    public Recurring(Recurring recurring) {
        super(recurring.getName(), recurring.getAmount(), recurring.getDate(), recurring.getCategory(),
                recurring.getRemarks());
        this.frequency = recurring.getFrequency();
        this.occurrence = recurring.getOccurrence();
        this.lastConvertedDate = LocalDate.now();
        this.recurringListOfExpenses = new ArrayList<>(occurrence.value);
        for (int i = 0; i < occurrence.value; i++) {
            Name newName = new Name(name.name + " (Recurring)");

            Date newDate = new Date(date);
            if (frequency.value.equals("D")) {
                newDate.setLocalDate(date.getLocalDate().plusDays(i));
            } else if (frequency.value.equals("M")) {
                newDate.setLocalDate(date.getLocalDate().plusMonths(i));
            } else if (frequency.value.equals("Y")) {
                newDate.setLocalDate(date.getLocalDate().plusYears(i));
            }

            Expense toAdd = new Expense(newName, amount, newDate, category, remarks);
            //System.out.println(toAdd);
            this.recurringListOfExpenses.add(toAdd);
        }
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public LocalDate getLastConvertedDate() {
        return lastConvertedDate;
    }

    public ArrayList<Expense> getRecurringListOfExpenses() {
        return recurringListOfExpenses;
    }

    /**
     * Returns true if both recurrings of the same name have the same amount, date, frequency, and occurrence.
     * This defines a weaker notion of equality between two recurrings.
     */
    public boolean isSameRecurring(Recurring otherRecurring) {
        if (otherRecurring == this) {
            return true;
        }

        return otherRecurring != null
                && otherRecurring.getName().equals(getName())
                && otherRecurring.getAmount().equals(getAmount())
                && otherRecurring.getDate().equals(getDate())
                && otherRecurring.getFrequency().equals(getFrequency())
                && otherRecurring.getOccurrence() == (getOccurrence());
    }

    /**
     * Returns true if both recurrings have the same identity and data fields.
     * This defines a stronger notion of equality between two recurrings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recurring)) {
            return false;
        }

        Recurring otherRecurring = (Recurring) other;
        return otherRecurring.getName().equals(getName())
                && otherRecurring.getAmount().equals(getAmount())
                && otherRecurring.getDate().equals(getDate())
                && otherRecurring.getCategory().equals(getCategory())
                && otherRecurring.getRemarks().equals(getRemarks())
                && otherRecurring.getFrequency().equals(getFrequency())
                && otherRecurring.getOccurrence().equals(getOccurrence());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date, category, remarks, frequency, occurrence);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append(" Amount: ")
                .append(amount)
                .append(" Category: ")
                .append(category);

        if (date != null) {
            builder.append(" Date: ")
                    .append(date);
        }
        if (!remarks.equals("")) {
            builder.append(" Remarks: ")
                    .append(remarks);
        }

        builder.append(" Frequency: ")
                .append(frequency)
                .append(" Occurrence: ")
                .append(occurrence);
        return builder.toString();
    }
}
