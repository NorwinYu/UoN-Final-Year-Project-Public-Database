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
import seedu.address.model.debt.Debt;

/**
 * Panel containing the list of persons.
 */
public class DebtListPanel extends UiPart<Region> {
    private static final String FXML = "DebtListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DebtListPanel.class);

    @FXML
    private ListView<Debt> debtListView;

    public DebtListPanel(ObservableList<Debt> debtList, ObservableValue<Debt> selectedDebt,
                         Consumer<Debt> onSelectedDebtChange) {
        super(FXML);
        debtListView.setItems(debtList);
        debtListView.setCellFactory(listView -> new DebtListViewCell());
        debtListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in debt list panel changed to : '" + newValue + "'");
            onSelectedDebtChange.accept(newValue);
        });
        selectedDebt.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected debt changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected debt,
            // otherwise we would have an infinite loop.
            if (Objects.equals(debtListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                debtListView.getSelectionModel().clearSelection();
            } else {
                int index = debtListView.getItems().indexOf(newValue);
                debtListView.scrollTo(index);
                debtListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Debt} using a {@code DebtCard}.
     */
    class DebtListViewCell extends ListCell<Debt> {
        @Override
        protected void updateItem(Debt debt, boolean empty) {
            super.updateItem(debt, empty);

            if (empty || debt == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DebtCard(debt, getIndex() + 1).getRoot());
            }
        }
    }

}
