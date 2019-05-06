package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final ExpenseListPanelHandle expenseListPanel;
    private final BudgetListPanelHandle budgetListPanel;
    private final DebtListPanelHandle debtListPanel;
    private final RecurringListPanelHandle recurringListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        expenseListPanel = new ExpenseListPanelHandle(getChildNode(ExpenseListPanelHandle.EXPENSE_LIST_VIEW_ID));
        budgetListPanel = new BudgetListPanelHandle(getChildNode(BudgetListPanelHandle.BUDGET_LIST_VIEW_ID));
        debtListPanel = new DebtListPanelHandle(getChildNode(DebtListPanelHandle.DEBT_LIST_VIEW_ID));
        recurringListPanel = new RecurringListPanelHandle(getChildNode(RecurringListPanelHandle
                .RECURRING_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public ExpenseListPanelHandle getExpenseListPanel() {
        return expenseListPanel;
    }

    public BudgetListPanelHandle getBudgetListPanel() {
        return budgetListPanel;
    }

    public DebtListPanelHandle getDebtListPanel() {
        return debtListPanel;
    }

    public RecurringListPanelHandle getRecurringListPanel() {
        return recurringListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
