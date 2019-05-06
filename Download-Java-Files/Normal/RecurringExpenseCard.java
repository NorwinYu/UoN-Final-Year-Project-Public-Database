package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recurring.Recurring;

/**
 * An UI component that displays information of a {@code Recurring}.
 */
public class RecurringExpenseCard extends UiPart<Region> {

    private static final String FXML = "RecurringExpenseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinanceTracker level 4</a>
     */

    public final Recurring recurringExpense;

    @FXML
    private HBox recurringExpenseCardPane;
    @FXML
    private Label recurringExpenseName;
    @FXML
    private Label recurringExpenseId;
    @FXML
    private Label recurringExpenseAmount;
    @FXML
    private Label recurringExpenseCategory;
    @FXML
    private Label recurringExpenseDate;


    public RecurringExpenseCard(Recurring recurringExpense, int displayedIndex) {
        super(FXML);
        this.recurringExpense = recurringExpense;
        recurringExpenseId.setText(displayedIndex + ". ");
        recurringExpenseAmount.setText("$" + recurringExpense.getAmount().toString());
        recurringExpenseName.setText(recurringExpense.getName().name);
        recurringExpenseCategory.setText(recurringExpense.getCategory().toString());
        recurringExpenseDate.setText(recurringExpense.getDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecurringExpenseCard)) {
            return false;
        }

        // state check
        RecurringExpenseCard card = (RecurringExpenseCard) other;
        return recurringExpenseId.getText().equals(card.recurringExpenseId.getText())
                && recurringExpense.equals(card.recurringExpense);
    }
}
