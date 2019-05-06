package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.debt.Debt;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class DebtCard extends UiPart<Region> {

    private static final String FXML = "DebtListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinanceTracker level 4</a>
     */

    public final Debt debt;

    @FXML
    private HBox debtCardPane;
    @FXML
    private Label borrowerName;
    @FXML
    private Label debtId;
    @FXML
    private Label debtAmount;
    @FXML
    private Label debtCategory;
    @FXML
    private Label debtDue;


    public DebtCard(Debt debt, int displayedIndex) {
        super(FXML);
        this.debt = debt;
        debtId.setText(displayedIndex + ". ");
        debtAmount.setText("$" + debt.getAmount().toString());
        borrowerName.setText(debt.getPersonOwed().name);
        debtCategory.setText(debt.getCategory().toString());
        debtDue.setText(debt.getDeadline().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DebtCard)) {
            return false;
        }

        // state check
        DebtCard card = (DebtCard) other;
        return debtId.getText().equals(card.debtId.getText())
                && debt.equals(card.debt);
    }
}
