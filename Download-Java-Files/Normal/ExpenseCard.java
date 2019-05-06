package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinanceTracker level 4</a>
     */

    public final Expense expense;

    @FXML
    private HBox expenseCardPane;
    @FXML
    private Label expenseName;
    @FXML
    private Label expenseId;
    @FXML
    private Label expenseAmount;
    @FXML
    private Label expenseCategory;
    @FXML
    private Label expenseDate;


    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        expenseId.setText(displayedIndex + ". ");
        expenseAmount.setText("$" + expense.getAmount().toString());
        expenseName.setText(expense.getName().name);
        expenseCategory.setText(expense.getCategory().toString());
        expenseDate.setText(expense.getDate().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseCard)) {
            return false;
        }

        // state check
        ExpenseCard card = (ExpenseCard) other;
        return expenseId.getText().equals(card.expenseId.getText())
                && expense.equals(card.expense);
    }
}
