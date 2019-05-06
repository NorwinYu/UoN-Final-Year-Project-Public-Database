package edu.mum.library.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.common.LibraryException;
import edu.mum.library.dataaccess.BookDao;
import edu.mum.library.model.Book;
import edu.mum.library.model.CheckoutEntry;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseFxController;
import edu.mum.library.view.dto.OverdueDto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OverduesOverviewController extends BaseFxController {
	@FXML
	private TableColumn<OverdueDto, String> copyNumberColumn;
	@FXML
	private TableColumn<OverdueDto, LocalDate> dueDateColumn;
	@FXML
	private TableColumn<OverdueDto, String> memberIdColumn;
	@FXML
	private TableColumn<OverdueDto, Boolean> overdueColumn;

	@FXML
	private TableView<OverdueDto> overduesTable;

	@FXML
	private TextField searchByIsbnField;

	@FXML
	private Label availableCopiesField;
	@FXML
	private Label checkedoutCopiesField;
	@FXML
	private Label titleField;

	@Autowired
	private BookDao bookDao;
	@Autowired
	private LibraryService libraryService;

	private void configureColumnForTableView() {

		copyNumberColumn.setCellValueFactory(new PropertyValueFactory<>("copyNumber"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		overdueColumn.setCellValueFactory(new PropertyValueFactory<>("overdue"));
		overdueColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

	}

	/**
	 * Called when the user clicks the search button. Fills TableView with entries
	 */
	@FXML
	private void handleSearch() {

		try {
			List<OverdueDto> result = new ArrayList<>();

			for (CheckoutEntry ce : libraryService.getOverdues(searchByIsbnField.getText())) {
				Boolean overdue = ce.getDueDate().isBefore(LocalDate.now());
				OverdueDto toAdd = new OverdueDto(ce.getBookCopy().getCopyNumber(), ce.getDueDate(),
						ce.getMember().getMemberId(), overdue);
				result.add(toAdd);
			}
			Book book = bookDao.readById(searchByIsbnField.getText());
			int checkoutCounter = (int) book.getBookCopies().stream().filter(cp -> !cp.isAvailable()).count();
			int availableCounter = book.getBookCopies().size() - checkoutCounter;
			checkedoutCopiesField.setText(checkoutCounter + "");
			availableCopiesField.setText(availableCounter + "");
			titleField.setText(book.getTitle());

			overduesTable.setItems(FXCollections.observableArrayList(result));

		} catch (LibraryException e) {
			// TODO Auto-generated catch block
			fxViewManager.showError(this.getCurrentStage(), e.getMessage(), "Error Title", "Header Error");
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		configureColumnForTableView();

	}
}
