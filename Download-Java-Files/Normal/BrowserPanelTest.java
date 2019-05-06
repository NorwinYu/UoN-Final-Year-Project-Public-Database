package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalBudgets.FOOD_BUDGET;
import static seedu.address.testutil.TypicalDebts.AMY;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalRecurrings.PHONE_BILL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;

public class BrowserPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Debt> selectedDebt = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Recurring> selectedRecurring = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Statistics> statistics = new SimpleObjectProperty<>();

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedExpense, selectedDebt, selectedBudget,
                selectedRecurring, statistics));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of an expense
        guiRobot.interact(() -> selectedExpense.set(DUCK_RICE));
        String expectedExpenseTitle = BrowserPanel.EXPENSE_PAGE_TITLE;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedExpenseTitle, browserPanelHandle.getLoadedUrlTitle());

        // associated web page of a debt
        guiRobot.interact(() -> selectedDebt.set(AMY));
        String expectedDebtTitle = BrowserPanel.DEBT_PAGE_TITLE;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedDebtTitle, browserPanelHandle.getLoadedUrlTitle());

        // associated web page of a debt
        guiRobot.interact(() -> selectedBudget.set(FOOD_BUDGET));
        String expectedBudgetTitle = BrowserPanel.BUDGET_PAGE_TITLE;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedBudgetTitle, browserPanelHandle.getLoadedUrlTitle());

        // associated web page of a recurring
        guiRobot.interact(() -> selectedRecurring.set(PHONE_BILL));
        String expectedRecurringTitle = BrowserPanel.RECURRING_PAGE_TITLE;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecurringTitle, browserPanelHandle.getLoadedUrlTitle());
    }
}
