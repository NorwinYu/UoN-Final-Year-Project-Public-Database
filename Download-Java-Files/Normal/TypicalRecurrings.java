package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FinanceTracker;
import seedu.address.model.recurring.Recurring;

/**
 * A utility class containing a list of {@code Recurring} objects to be used in tests.
 */
public class TypicalRecurrings {
    public static final Recurring PHONE_BILL = new RecurringBuilder().withName("Phone Bill")
            .withAmount("50.00").withCategory("utilities").withDate("23-02-2019").withFrequency("M")
            .withOccurrence("24").withRemarks("Signed a new 2 year plan.").build();
    public static final Recurring DAILY_LUNCH = new RecurringBuilder().withName("Daily Lunch")
            .withAmount("5.00").withCategory("food").withDate("01-01-2019").withFrequency("D")
            .withOccurrence("365").withRemarks("Eat the same $5 meal everyday for a year.").build();
    public static final Recurring SPOTIFY_SUBSCRIPTION = new RecurringBuilder().withName("Spotify Subscription")
            .withAmount("20.00").withCategory("entertainment").withDate("24-09-2019").withFrequency("M")
            .withOccurrence("12").withRemarks("Spotify for a year!").build();

    public static final Recurring RECURRING = new RecurringBuilder().withName("EPL Subscription")
            .withAmount("40").withCategory("utilities").withDate("01-01-2019").withFrequency("M")
            .withOccurrence("12").withRemarks("Football").build();

    public static final Recurring RECURRING_WITHOUT_REMARKS = new RecurringBuilder().withName("EPL Subscription")
            .withAmount("40").withCategory("utilities").withDate("01-01-2019").withFrequency("M")
            .withOccurrence("12").build();

    private TypicalRecurrings() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical recurrings.
     */
    public static FinanceTracker getTypicalFinanceTrackerWithRecurrings() {
        FinanceTracker ft = new FinanceTracker();
        for (Recurring recurring : getTypicalRecurring()) {
            ft.addRecurring(recurring);
        }
        return ft;
    }

    public static List<Recurring> getTypicalRecurring() {
        return new ArrayList<>(Arrays.asList(PHONE_BILL, DAILY_LUNCH, SPOTIFY_SUBSCRIPTION));
    }
}

