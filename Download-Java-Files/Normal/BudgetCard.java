package seedu.address.ui;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinanceTracker level 4</a>
     */

    public final Budget budget;

    @FXML
    private HBox budgetCardPane;
    @FXML
    private Label budgetCategory;
    @FXML
    private Label budgetAmount;
    @FXML
    private Label budgetDuration;
    @FXML
    private Label budgetSpent;


    public BudgetCard(Budget budget) {
        super(FXML);
        this.budget = budget;
        budgetCategory.setText(budget.getCategory().name());
        budgetAmount.setText("$" + budget.getAmount().toString());
        budgetDuration.setText(budget.getStartDate().toString() + " till " + budget.getEndDate().toString());
        DecimalFormat totalSpentFormat = new DecimalFormat("0.00");
        budgetSpent.setText("$" + totalSpentFormat.format((double) budget.getTotalSpent() / 100) + " ("
                + budget.getPercentage() + "%) spent");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetCard)) {
            return false;
        }

        // state check
        BudgetCard card = (BudgetCard) other;
        return budgetCategory.getText().equals(card.budgetCategory.getText())
                && budget.equals(card.budget);
    }
}
