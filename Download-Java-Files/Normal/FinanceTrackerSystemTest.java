package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.BudgetCardHandle;
import guitests.guihandles.BudgetListPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DebtListPanelHandle;
import guitests.guihandles.ExpenseCardHandle;
import guitests.guihandles.ExpenseListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.RecurringListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;

import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.budgetcommands.ClearBudgetCommand;
import seedu.address.logic.commands.budgetcommands.SelectBudgetCommand;
import seedu.address.logic.commands.debtcommands.ClearDebtCommand;
import seedu.address.logic.commands.expensecommands.ClearExpenseCommand;
import seedu.address.logic.commands.expensecommands.ListExpenseCommand;
import seedu.address.logic.commands.expensecommands.SelectExpenseCommand;
import seedu.address.logic.commands.generalcommands.FindCommand;
import seedu.address.logic.commands.recurringcommands.ClearRecurringCommand;
import seedu.address.model.FinanceTracker;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.View;
import seedu.address.testutil.TypicalFinanceTracker;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;

/**
 * A system test class for FinanceTracker, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class FinanceTrackerSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected FinanceTracker getInitialData() {
        return TypicalFinanceTracker.getTypicalFinanceTracker();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public ExpenseListPanelHandle getExpenseListPanel() {
        return mainWindowHandle.getExpenseListPanel();
    }

    public BudgetListPanelHandle getBudgetListPanel() {
        return mainWindowHandle.getBudgetListPanel();
    }

    public DebtListPanelHandle getDebtListPanel() {
        return mainWindowHandle.getDebtListPanel();
    }

    public RecurringListPanelHandle getRecurringListPanel() {
        return mainWindowHandle.getRecurringListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all expenses in the finance tracker.
     */
    protected void showAllExpenses() {
        executeCommand(ListExpenseCommand.COMMAND_WORD + " " + PREFIX_VIEW + View.ALL);
        assertEquals(getModel().getFinanceTracker().getExpenseList().size(),
                getModel().getFilteredExpenseList().size());
    }

    /**
     * Displays all budgets in the finance tracker.
     */
    protected void showAllBudgets() {
        executeCommand(ListExpenseCommand.COMMAND_WORD + " " + PREFIX_VIEW + View.ALL);
        assertEquals(getModel().getFinanceTracker().getExpenseList().size(),
                getModel().getFilteredExpenseList().size());
    }

    /**
     * Displays all expenses with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showExpensesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredExpenseList().size()
                <= getModel().getFinanceTracker().getExpenseList().size());
    }

    /**
     * Selects the expense at {@code index} of the displayed list.
     */
    protected void selectExpense(Index index) {
        executeCommand(SelectExpenseCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getExpenseListPanel().getSelectedCardIndex());
    }

    /**
     * Selects the budget of {@code category} of the displayed list.
     */
    protected void selectBudget(Category category) {
        executeCommand(SelectBudgetCommand.COMMAND_WORD + " " + PREFIX_CATEGORY + category.toString());
        assertEquals(category, getBudgetListPanel().getSelectedCardCategory());
    }

    /**
     * Deletes all expenses in the finance tracker.
     */
    protected void deleteAllExpenses() {
        executeCommand(ClearExpenseCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinanceTracker().getExpenseList().size());
    }

    /**
     * Deletes all budgets in the finance tracker.
     */
    protected void deleteAllBudgets() {
        executeCommand(ClearBudgetCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinanceTracker().getBudgetList().size());
    }

    /**
     * Deletes all debts in the finance tracker.
     */
    protected void deleteAllDebts() {
        executeCommand(ClearDebtCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinanceTracker().getDebtList().size());
    }

    /**
     * Deletes all recurring expenses in the finance tracker.
     */
    protected void deleteAllRecurrings() {
        executeCommand(ClearRecurringCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinanceTracker().getRecurringList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same expense objects as {@code expectedModel}
     * and the expense list panel displays the expenses in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new FinanceTracker(expectedModel.getFinanceTracker()), testApp.readStorageAddressBook());
        assertListMatching(getExpenseListPanel(), expectedModel.getFilteredExpenseList());
        assertListMatching(getBudgetListPanel(), expectedModel.getFilteredBudgetList());
        assertListMatching(getDebtListPanel(), expectedModel.getFilteredDebtList());
        assertListMatching(getRecurringListPanel(), expectedModel.getFilteredRecurringList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code ExpenseListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        // getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getExpenseListPanel().rememberSelectedExpenseCard();
        getBudgetListPanel().rememberSelectedBudgetCard();
        getDebtListPanel().rememberSelectedDebtCard();
        getRecurringListPanel().rememberSelectedRecurringCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getExpenseListPanel().isAnyCardSelected());
        assertFalse(getBudgetListPanel().isAnyCardSelected());
        assertFalse(getDebtListPanel().isAnyCardSelected());
        assertFalse(getRecurringListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the expense in the expense list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see ExpenseListPanelHandle#isSelectedExpenseCardChanged()
     */
    protected void assertSelectedExpenseCardChanged(Index expectedSelectedCardIndex) {
        getExpenseListPanel().navigateToCard(getExpenseListPanel().getSelectedCardIndex());
        ExpenseCardHandle selectedCard = getExpenseListPanel().getHandleToSelectedCard();

        // URL expectedUrl;
        // URL actualUrl;
        // expectedUrl = BrowserPanel.EXPENSES_PAGE_URL;
        // actualUrl = BrowserPanel.getCurrentObjectPageUrl();
        String expectedPageTitle = BrowserPanel.EXPENSE_PAGE_TITLE;
        String actualPageTitle = getBrowserPanel().getLoadedUrlTitle();

        // assertEquals(expectedUrl, actualUrl);
        assertEquals(expectedPageTitle, actualPageTitle);

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getExpenseListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the expense list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see ExpenseListPanelHandle#isSelectedExpenseCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        // assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getExpenseListPanel().isSelectedExpenseCardChanged());
        assertFalse(getBudgetListPanel().isSelectedBudgetCardChanged());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the budget in the budget list panel at
     * {@code expectedSelectedCardCategory}, and only the card of {@code expectedSelectedCardCategory} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see BudgetListPanelHandle#isSelectedBudgetCardChanged()
     */
    protected void assertSelectedBudgetCardChanged(Index expectedSelectedCardIndex) {
        getBudgetListPanel().navigateToCard(getBudgetListPanel().getSelectedCardIndex());
        BudgetCardHandle selectedCard = getBudgetListPanel().getHandleToSelectedCard();

        // URL expectedUrl;
        // URL actualUrl;
        // expectedUrl = BrowserPanel.EXPENSES_PAGE_URL;
        // actualUrl = BrowserPanel.getCurrentObjectPageUrl();
        String expectedPageTitle = BrowserPanel.BUDGET_PAGE_TITLE;
        String actualPageTitle = getBrowserPanel().getLoadedUrlTitle();

        // assertEquals(expectedUrl, actualUrl);
        assertEquals(expectedPageTitle, actualPageTitle);

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getExpenseListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the budget in the budget list panel at
     * {@code expectedSelectedCardCategory}, and only the card of {@code expectedSelectedCardCategory} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see BudgetListPanelHandle#isSelectedBudgetCardChanged()
     */
    protected void assertSelectedBudgetCardChanged(Category expectedSelectedCardCategory) {
        getBudgetListPanel().navigateToCard(getBudgetListPanel().getSelectedCardCategory());
        BudgetCardHandle selectedCard = getBudgetListPanel().getHandleToSelectedCard();

        // URL expectedUrl;
        // URL actualUrl;
        // expectedUrl = BrowserPanel.EXPENSES_PAGE_URL;
        // actualUrl = BrowserPanel.getCurrentObjectPageUrl();
        String expectedPageTitle = BrowserPanel.BUDGET_PAGE_TITLE;
        String actualPageTitle = getBrowserPanel().getLoadedUrlTitle();

        // assertEquals(expectedUrl, actualUrl);
        assertEquals(expectedPageTitle, actualPageTitle);

        assertEquals(expectedSelectedCardCategory, getBudgetListPanel().getSelectedCardCategory());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getExpenseListPanel(), getModel().getFilteredExpenseList());
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
