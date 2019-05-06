package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgets;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBudget;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.BudgetCardHandle;
import guitests.guihandles.BudgetListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.budget.Budget;

public class BudgetListPanelTest extends GuiUnitTest {
    private static final ObservableList<Budget> TYPICAL_BUDGETS =
            FXCollections.observableList(getTypicalBudgets());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private BudgetListPanelHandle budgetListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_BUDGETS);

        for (int i = 0; i < TYPICAL_BUDGETS.size(); i++) {
            budgetListPanelHandle.navigateToCard(TYPICAL_BUDGETS.get(i));
            Budget expectedBudget = TYPICAL_BUDGETS.get(i);
            BudgetCardHandle actualCard = budgetListPanelHandle.getBudgetCardHandle(i);

            assertCardDisplaysBudget(expectedBudget, actualCard);
        }
    }

    @Test
    public void selection_modelSelectedBudgetChanged_selectionChanges() {
        initUi(TYPICAL_BUDGETS);
        Budget secondBudget = TYPICAL_BUDGETS.get(INDEX_SECOND_EXPENSE.getZeroBased());
        guiRobot.interact(() -> selectedBudget.set(secondBudget));
        guiRobot.pauseForHuman();

        BudgetCardHandle expectedBudget = budgetListPanelHandle.getBudgetCardHandle(
                INDEX_SECOND_EXPENSE.getZeroBased());
        BudgetCardHandle selectedBudget = budgetListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedBudget, selectedBudget);
    }

    /**
     * Verifies that creating and deleting large number of budgets in {@code BudgetListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Budget> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of budget cards exceeded time limit");
    }

    /**
     * Returns a list of budgets containing {@code personCount} budgets that is used to populate the
     * {@code BudgetListPanel}.
     */
    private ObservableList<Budget> createBackingList(int budgetCount) {
        ObservableList<Budget> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < budgetCount; i++) {
            Amount amount = new Amount("1.0");
            Date startDate = new Date("13-03-2019");
            Date endDate = new Date("13-03-2020");
            Category category = Category.FOOD;
            Budget budget = new Budget(category, amount, startDate, endDate, null, 0, 0);
            backingList.add(budget);
        }
        return backingList;
    }

    /**
     * Initializes {@code budgetListPanelHandle} with a {@code BudgetListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BudgetListPanel}.
     */
    private void initUi(ObservableList<Budget> backingList) {
        BudgetListPanel budgetListPanel =
                new BudgetListPanel(backingList, selectedBudget, selectedBudget::set);
        uiPartRule.setUiPart(budgetListPanel);

        budgetListPanelHandle = new BudgetListPanelHandle(getChildNode(budgetListPanel.getRoot(),
                BudgetListPanelHandle.BUDGET_LIST_VIEW_ID));
    }
}
