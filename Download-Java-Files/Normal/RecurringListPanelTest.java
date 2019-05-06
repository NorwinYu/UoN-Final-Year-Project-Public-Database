package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.testutil.TypicalRecurrings.getTypicalRecurring;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRecurring;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.RecurringCardHandle;
import guitests.guihandles.RecurringListPanelHandle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.recurring.Recurring;

public class RecurringListPanelTest extends GuiUnitTest {
    private static final ObservableList<Recurring> TYPICAL_RECURRING =
            FXCollections.observableList(getTypicalRecurring());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Recurring> selectedRecurring = new SimpleObjectProperty<>();
    private RecurringListPanelHandle recurringListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RECURRING);

        for (int i = 0; i < TYPICAL_RECURRING.size(); i++) {
            recurringListPanelHandle.navigateToCard(TYPICAL_RECURRING.get(i));
            Recurring expectedRecurring = TYPICAL_RECURRING.get(i);
            RecurringCardHandle actualCard = recurringListPanelHandle.getRecurringCardHandle(i);

            assertCardDisplaysRecurring(expectedRecurring, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedRecurringChanged_selectionChanges() {
        initUi(TYPICAL_RECURRING);
        Recurring secondRecurring = TYPICAL_RECURRING.get(INDEX_SECOND_EXPENSE.getZeroBased());
        guiRobot.interact(() -> selectedRecurring.set(secondRecurring));
        guiRobot.pauseForHuman();

        RecurringCardHandle expectedRecurring = recurringListPanelHandle.getRecurringCardHandle(
                INDEX_SECOND_EXPENSE.getZeroBased());
        RecurringCardHandle selectedRecurring = recurringListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRecurring, selectedRecurring);
    }

    /**
     * Verifies that creating and deleting large number of recurrings in {@code RecurringListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Recurring> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of recurring cards exceeded time limit");
    }

    /**
     * Returns a list of recurrings containing {@code personCount} recurrings that is used to populate the
     * {@code RecurringListPanel}.
     */
    private ObservableList<Recurring> createBackingList(int recurringCount) {
        ObservableList<Recurring> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < recurringCount; i++) {
            Name name = new Name(i + "a");
            Amount amount = new Amount("0.11");
            Date date = new Date("13-01-2020");
            Frequency frequency = new Frequency("m");
            Occurrence occurrence = new Occurrence("5");
            Category category = Category.FOOD;
            Recurring recurring = new Recurring(name, amount, date, category, null, frequency, occurrence);
            backingList.add(recurring);
        }
        return backingList;
    }

    /**
     * Initializes {@code recurringListPanelHandle} with a {@code RecurringListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RecurringListPanel}.
     */
    private void initUi(ObservableList<Recurring> backingList) {
        RecurringExpenseListPanel recurringListPanel =
                new RecurringExpenseListPanel(backingList, selectedRecurring, selectedRecurring::set);
        uiPartRule.setUiPart(recurringListPanel);

        recurringListPanelHandle = new RecurringListPanelHandle(getChildNode(recurringListPanel.getRoot(),
                RecurringListPanelHandle.RECURRING_LIST_VIEW_ID));
    }
}
