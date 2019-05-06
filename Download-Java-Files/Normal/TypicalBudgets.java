package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BUDGET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_BUDGET;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FinanceTracker;
import seedu.address.model.budget.Budget;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
// SHOPPING, WORK, UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS;
public class TypicalBudgets {
    public static final Budget FOOD_BUDGET = new BudgetBuilder().withCategory("food").withAmount("600")
            .withStartDate("01-10-2019").withEndDate("31-10-2019").withRemarks("eat less save more").build();
    public static final Budget TRANSPORT_BUDGET = new BudgetBuilder().withCategory("transport").withAmount("300")
            .withStartDate("01-07-2019").withEndDate("31-12-2019").withRemarks("only for school and back").build();
    public static final Budget SHOPPING_BUDGET = new BudgetBuilder().withCategory("shopping").withAmount("200")
            .withStartDate("01-06-2019").withEndDate("30-06-2019").withRemarks("only for grocery shopping").build();
    public static final Budget WORK_BUDGET = new BudgetBuilder().withCategory("work").withAmount("100")
            .withStartDate("13-05-2019").withEndDate("02-08-2019").withRemarks("internship stuff").build();
    public static final Budget UTILITIES_BUDGET = new BudgetBuilder().withCategory("utilities").withAmount("450")
            .withStartDate("01-05-2019").withEndDate("31-05-2019").withRemarks("take shorter showers").build();
    public static final Budget HEALTHCARE_BUDGET = new BudgetBuilder().withCategory("healthcare").withAmount("100")
            .withStartDate("01-06-2019").withEndDate("15-06-2019").withRemarks("stress may make me feel sick").build();
    public static final Budget ENTERTAINMENT_BUDGET = new BudgetBuilder().withCategory("entertainment")
            .withAmount("900")
            .withStartDate("11-05-2019").withEndDate("11-08-2019").withRemarks("yay summer vacation").build();
    public static final Budget TRAVEL_BUDGET = new BudgetBuilder().withCategory("travel").withAmount("7000")
            .withStartDate("01-01-2020").withEndDate("31-12-2020").withRemarks("grad trip and family trip").build();

    // Manually added
    /*public static final Budget BUDGET1 = new BudgetBuilder().withCategory("travel").withAmount("500")
        .withStartDate("01-06-2019").withEndDate("15-05-2020").withRemarks("spend less before grad").build();
    public static final Budget BUDGET2 = new BudgetBuilder().withCategory("others").withAmount("1000")
            .withStartDate("15-05-2019").withEndDate("31-12-2019").withRemarks("time to invest").build();
    */
    // Manually added - Budget's details found in {@code CommandTestUtil}
    public static final Budget BUDGET = new BudgetBuilder().withCategory(VALID_CATEGORY_BUDGET)
            .withAmount(VALID_AMOUNT_BUDGET).withStartDate(VALID_STARTDATE_BUDGET).withEndDate(VALID_ENDDATE_BUDGET)
            .withRemarks(VALID_REMARKS_BUDGET).build();
    public static final Budget BUDGET_WITH_TODAYS_DATE = new BudgetBuilder().withCategory(VALID_CATEGORY_BUDGET)
            .withAmount(VALID_AMOUNT_BUDGET).withStartDate(LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-uuuu"))).withEndDate(VALID_ENDDATE_BUDGET)
            .withRemarks(VALID_REMARKS_BUDGET).build();
    public static final Budget BUDGET_WITHOUT_REMARKS = new BudgetBuilder().withCategory(VALID_CATEGORY_BUDGET)
            .withAmount(VALID_AMOUNT_BUDGET).withStartDate(VALID_STARTDATE_BUDGET).withEndDate(VALID_ENDDATE_BUDGET)
            .withRemarks(null).build();


    private TypicalBudgets() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical budgets.
     */
    public static FinanceTracker getTypicalFinanceTrackerWithBudgets() {
        FinanceTracker ft = new FinanceTracker();
        for (Budget budget : getTypicalBudgets()) {
            ft.addBudget(budget);
        }
        return ft;
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(FOOD_BUDGET, TRANSPORT_BUDGET, SHOPPING_BUDGET, WORK_BUDGET,
                UTILITIES_BUDGET, HEALTHCARE_BUDGET, ENTERTAINMENT_BUDGET, TRAVEL_BUDGET));
    }
}
