package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalDebts.getTypicalDebts;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDebt;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.DebtCardHandle;
import guitests.guihandles.DebtListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Name;
import seedu.address.model.debt.Debt;

public class DebtListPanelTest extends GuiUnitTest {
    private static final ObservableList<Debt> TYPICAL_DEBTS =
            FXCollections.observableList(getTypicalDebts());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Debt> selectedDebt = new SimpleObjectProperty<>();
    private DebtListPanelHandle debtListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_DEBTS);

        for (int i = 0; i < TYPICAL_DEBTS.size(); i++) {
            debtListPanelHandle.navigateToCard(TYPICAL_DEBTS.get(i));
            Debt expectedDebt = TYPICAL_DEBTS.get(i);
            DebtCardHandle actualCard = debtListPanelHandle.getDebtCardHandle(i);

            assertCardDisplaysDebt(expectedDebt, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_DEBTS);
        Debt secondDebt = TYPICAL_DEBTS.get(INDEX_SECOND_EXPENSE.getZeroBased());
        guiRobot.interact(() -> selectedDebt.set(secondDebt));
        guiRobot.pauseForHuman();

        DebtCardHandle expectedDebt = debtListPanelHandle.getDebtCardHandle(
                INDEX_SECOND_EXPENSE.getZeroBased());
        DebtCardHandle selectedDebt = debtListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedDebt, selectedDebt);
    }

    /**
     * Verifies that creating and deleting large number of debts in {@code DebtListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Debt> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of debt cards exceeded time limit");
    }

    /**
     * Returns a list of debts containing {@code personCount} debts that is used to populate the
     * {@code DebtListPanel}.
     */
    private ObservableList<Debt> createBackingList(int debtCount) {
        ObservableList<Debt> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < debtCount; i++) {
            Name name = new Name(i + "a");
            Amount amount = new Amount("0.01");
            Date date = new Date("13-03-2019");
            Category category = Category.FOOD;
            Debt debt = new Debt(name, amount, date, category, null);
            backingList.add(debt);
        }
        return backingList;
    }

    /**
     * Initializes {@code debtListPanelHandle} with a {@code DebtListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code DebtListPanel}.
     */
    private void initUi(ObservableList<Debt> backingList) {
        DebtListPanel debtListPanel =
                new DebtListPanel(backingList, selectedDebt, selectedDebt::set);
        uiPartRule.setUiPart(debtListPanel);

        debtListPanelHandle = new DebtListPanelHandle(getChildNode(debtListPanel.getRoot(),
                DebtListPanelHandle.DEBT_LIST_VIEW_ID));
    }
}
