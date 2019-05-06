package seedu.address.model.util;

import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.expense.Expense;


/**
 * Contains utility methods for populating {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Chicken Rice"), new Amount("1021"), new Date("11-12-2018"),
                Category.FOOD, "Bishan Chicken Rice"),
            new Expense(new Name("Laksa"), new Amount("520"), new Date("03-02-2019"),
                Category.FOOD, null),
            new Expense(new Name("Japan Ticket"), new Amount("62040"), new Date("21-01-2019"),
                Category.TRAVEL, null),
            new Expense(new Name("Top up EZ link card"), new Amount("2000"), new Date("03-03-2019"),
                Category.TRANSPORT, "Topped up at KR MRT"),
            new Expense(new Name("See doctor"), new Amount("6520"), new Date("26-02-2019"),
                Category.HEALTHCARE, "For fever at UHC"),
            new Expense(new Name("Telephone bills"), new Amount("2510"), new Date("28-02-2019"),
                Category.UTILITIES, "Exceeded limit by 1 GB")
        };
    }

    public static ReadOnlyFinanceTracker getSampleFinanceTracker() {
        FinanceTracker sampleAb = new FinanceTracker();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleAb.addExpense(sampleExpense);
        }
        return sampleAb;
    }
}
