package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;

/**
 * Panel containing the list of persons.
 */
public class BudgetListPanel extends UiPart<Region> {
    private static final String FXML = "BudgetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetListPanel.class);

    @FXML
    private ListView<Budget> budgetListView;

    public BudgetListPanel(ObservableList<Budget> budgetList, ObservableValue<Budget> selectedBudget,
                           Consumer<Budget> onSelectedBudgetChange) {
        super(FXML);
        budgetListView.setItems(budgetList);
        budgetListView.setCellFactory(listView -> new BudgetListViewCell());
        budgetListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in budget list panel changed to : '" + newValue + "'");
            onSelectedBudgetChange.accept(newValue);
        });
        selectedBudget.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected budget changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected Budget,
            // otherwise we would have an infinite loop.
            if (Objects.equals(budgetListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                budgetListView.getSelectionModel().clearSelection();
            } else {
                int index = budgetListView.getItems().indexOf(newValue);
                budgetListView.scrollTo(index);
                budgetListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Budget} using a {@code BudgetCard}.
     */
    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget budget, boolean empty) {
            super.updateItem(budget, empty);

            if (empty || budget == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BudgetCard(budget).getRoot());
            }
        }
    }

}
