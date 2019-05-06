package edu.mum.library.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.dataaccess.BookDao;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseFxController;
import edu.mum.library.view.dto.BookDto;
import edu.mum.library.view.util.LibraryUiManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BookOverviewController extends BaseFxController {
	@FXML
	private TableView<BookDto> personTable;
	@FXML
	private TableColumn<BookDto, Integer> availabilityColumn;
	@FXML
	private TableColumn<BookDto, String> isbnColumn;
	@FXML
	private TableColumn<BookDto, Integer> numberofCopiesColumn;
	@FXML
	private TableColumn<BookDto, String> titleColumn;

	@FXML
	private TextField searchFilter;

	@Autowired
	private BookDao bookDao;
	@Autowired
	private LibraryService libraryService;
	@Autowired
	private LibraryUiManager libraryUiManager;

	@FXML
	public void addCopy() {
		BookDto selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			libraryService.addBookCopy(bookDao.readById(selectedPerson.getIsbn()));
			selectedPerson.setCopies("" + bookDao.readById(selectedPerson.getIsbn()).getBookCopies().size());
			fxViewManager.showInformation(application.getPrimaryStage(),
					"You have added a copy for:" + selectedPerson.getIsbn(), "Operation finished Successfully.",
					"Prompt");
		} else {
			fxViewManager.showError(application.getPrimaryStage(), "Please select a book!", "No book selected",
					"No Selection");
		}

	}

	private void configureColumnForTableView() {

		isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
		numberofCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("copies"));
	}

	List<BookDto> getAllMemberList() {
		return bookDao.getAll().stream().filter((book) -> book.getIsbn().startsWith(searchFilter.getText()))
				.map(m -> new BookDto(m)).collect(Collectors.toList());
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditBook() {
		BookDto selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = libraryUiManager.showBookEditDialog(selectedPerson);
			if (okClicked) {

			}

		} else {
			// Nothing selected.
			fxViewManager.showError(application.getPrimaryStage(), "Please select a book!", "No book selected",
					"No Selection");
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewBook() {
		BookDto tempPerson = null;// new Person();
		if (libraryUiManager.showBookEditDialog(tempPerson)) {
			// personTable.setItems(FXCollections.observableArrayList(getAllMemberList()));
			searchFilter.setText("");
			updateDataToTableView();
		}
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		configureColumnForTableView();
		updateDataToTableView();
	}

	@FXML
	public void updateDataToTableView() {
		personTable.setItems(FXCollections.observableArrayList(getAllMemberList()));
	}
}
