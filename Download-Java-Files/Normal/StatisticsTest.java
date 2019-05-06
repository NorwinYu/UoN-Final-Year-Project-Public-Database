package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalStatistics.BLANKTABLEHTMLCOMPARE;
import static seedu.address.testutil.TypicalStatistics.BLANKTABLEHTMLSTATS;
import static seedu.address.testutil.TypicalStatistics.DUCKRICETABLEHTMLCOMPARE;
import static seedu.address.testutil.TypicalStatistics.DUCKRICETABLEHTMLSTATS;
import static seedu.address.testutil.TypicalStatistics.DUCKRICETABLEHTMLTREND;
import static seedu.address.testutil.TypicalStatistics.EMPTYTABLEHTMLTREND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseList;
import seedu.address.testutil.Assert;

public class StatisticsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ExpenseList expenseList = new ExpenseList();
    private FilteredList<Expense> statsExpenses = new FilteredList<Expense>(
            expenseList.asUnmodifiableObservableList());

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Statistics(null));
    }

    @Test
    public void basicStatsParametersNull_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        //startDate cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("stats", null, date, null));
        //endDate cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("stats", date, null, null));
    }

    @Test
    public void compareStatsParametersNull_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");
        //date1 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", null, date, frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", date, null, frequency));
        //frequency cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("compare", date, date, null));
    }

    @Test
    public void trendStatsParametersNull_throwsNullPointerException() {
        Statistics stats = new Statistics(statsExpenses);
        Date date = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");
        //date1 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", null, date, frequency));
        //date2 cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", date, null, frequency));
        //frequency cannot be null
        Assert.assertThrows(NullPointerException.class, () ->
                stats.calculateStats("trend", date, date, null));
    }

    @Test
    public void startDateAfterEndDateProducesEmptyTable() {
        Statistics stats = new Statistics(statsExpenses);
        Date date1 = new Date("01-02-2019");
        Date date2 = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");

        //stats
        stats.calculateStats("stats", date1, date2, null);
        String outputHtmlStats = stats.getHtml();
        assertTrue(outputHtmlStats.equals("Statistics Summary <br>\n"
                + "From: 01-02-2019 To: 01-01-2019\n" + BLANKTABLEHTMLSTATS));

        //statscompare
        stats.calculateStats("trend", date1, date2, frequency);
        String outputHtmlTrend = stats.getHtml();
        assertTrue(outputHtmlTrend.equals("Statistics Trend <br>\n"
                + "From: 01-02-2019 To: 01-01-2019 Period Length: M<br>\n" + EMPTYTABLEHTMLTREND));

    }

    @Test
    public void statsCommandCorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date startDate = new Date("01-01-2018");
        Date endDate = new Date("02-01-2020");

        //stats with duck rice in range
        stats.calculateStats("stats", startDate, endDate, null);
        String outputHtmlStatsInRange = stats.getHtml();
        assertTrue(outputHtmlStatsInRange.equals("Statistics Summary <br>\n"
                + "From: 01-01-2018 To: 02-01-2020\n"
                + DUCKRICETABLEHTMLSTATS));

        startDate = new Date("02-01-2020");
        //stats with duck rice out of range
        stats.calculateStats("stats", startDate, endDate, null);
        String outputHtmlStatsOutOfRange = stats.getHtml();
        assertFalse(outputHtmlStatsOutOfRange.equals("Statistics Summary <br>\n"
                + "From: 02-01-2020 To: 01-01-2020\n"
                + DUCKRICETABLEHTMLSTATS));
        assertTrue(outputHtmlStatsOutOfRange.equals("Statistics Summary <br>\n"
                + "From: 02-01-2020 To: 02-01-2020\n"
                + BLANKTABLEHTMLSTATS));
    }

    @Test
    public void statsCompareCommandCorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date date1 = new Date("01-01-2019");
        Date date2 = new Date("01-01-2019");
        Frequency frequency = new Frequency("M");

        //statsCompare with duck rice in range
        stats.calculateStats("compare", date1, date2, frequency);
        String outputHtmlStatsCompareInRange = stats.getHtml();
        assertTrue(outputHtmlStatsCompareInRange.equals("Statistics Compare <br>\n"
                + "From: 01-01-2019 To: 01-02-2019\n"
                + DUCKRICETABLEHTMLCOMPARE
                + "<br>From: 01-01-2019 To: 01-02-2019\n"
                + DUCKRICETABLEHTMLCOMPARE));

        date1 = new Date("02-01-2020");
        date2 = new Date("02-01-2020");
        //statsCompare with duck rice out of range
        stats.calculateStats("compare", date1, date2, frequency);
        String outputHtmlStatsCompareOutOfRange = stats.getHtml();
        assertFalse(outputHtmlStatsCompareOutOfRange.equals("Statistics Compare <br>\n"
                + "From: 02-01-2020 To: 02-02-2020\n"
                + DUCKRICETABLEHTMLCOMPARE
                + "<br>From: 02-01-2020 To: 02-02-2020\n"
                + DUCKRICETABLEHTMLCOMPARE));
        assertTrue(outputHtmlStatsCompareOutOfRange.equals("Statistics Compare <br>\n"
                + "From: 02-01-2020 To: 02-02-2020\n"
                + BLANKTABLEHTMLCOMPARE
                + "<br>From: 02-01-2020 To: 02-02-2020\n"
                + BLANKTABLEHTMLCOMPARE));
    }

    @Test
    public void statsTrendCommandCorrectResults() {
        ExpenseList expenseListWithDuckRice = new ExpenseList();
        expenseListWithDuckRice.add(DUCK_RICE);
        FilteredList<Expense> statsExpensesWithDuckRice = new FilteredList<>(
                expenseListWithDuckRice.asUnmodifiableObservableList());

        Statistics stats = new Statistics(statsExpensesWithDuckRice);
        Date startDate = new Date("01-01-2019");
        Date endDate = new Date("02-01-2019");
        Frequency frequency = new Frequency("M");

        //statsTrend with duck rice in range
        stats.calculateStats("trend", startDate, endDate, frequency);
        String outputHtmlStatsTrendInRange = stats.getHtml();
        //System.out.println(outputHtmlStatsCompare_InRange);
        assertTrue(outputHtmlStatsTrendInRange.equals("Statistics Trend <br>\n"
                + "From: 01-01-2019 To: 02-01-2019 Period Length: M<br>\n"
                + DUCKRICETABLEHTMLTREND));

        startDate = new Date("02-01-2019");
        //statsTrend with duck rice out of range
        stats.calculateStats("trend", startDate, endDate, frequency);
        String outputHtmlStatsTrendOutOfRange = stats.getHtml();
        assertFalse(outputHtmlStatsTrendOutOfRange.equals("Statistics Trend <br>\n"
                + "From: 02-01-2019 To: 02-01-2019 Period Length: M<br>\n"
                + DUCKRICETABLEHTMLTREND));

        assertTrue(outputHtmlStatsTrendOutOfRange.equals("Statistics Trend <br>\n"
                + "From: 02-01-2019 To: 02-01-2019 Period Length: M<br>\n"
                + EMPTYTABLEHTMLTREND));
    }


}
