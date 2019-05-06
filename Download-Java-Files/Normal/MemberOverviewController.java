package edu.mum.library.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.dataaccess.MemberDao;
import edu.mum.library.view.base.BaseFxController;
import edu.mum.library.view.dto.MemberDto;
import edu.mum.library.view.util.LibraryUiManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MemberOverviewController extends BaseFxController {
	@FXML
	private TableView<MemberDto> personTable;
	@FXML
	private TableColumn<MemberDto, String> firstNameColumn;
	@FXML
	private TableColumn<MemberDto, String> lastNameColumn;
	@FXML
	private TableColumn<MemberDto, String> memberIdColumn;

	@FXML
	private Label memberIdLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label phoneNumberLabel;

	@Autowired
	private LibraryUiManager libraryUiManager;

	@Autowired
	private MemberDao memberDao;

	List<MemberDto> getAllMemberList() {
		return memberDao.getAll().stream().map(m -> new MemberDto(m)).collect(Collectors.toList());
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		personTable.setItems(FXCollections.observableArrayList(getAllMemberList()));
		configureColumnForTableView();
		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	private void configureColumnForTableView() {

		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
	}

	private void showPersonDetails(MemberDto person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(person.getZipcode());
			cityLabel.setText(person.getCity());
			memberIdLabel.setText(person.getMemberId());
			stateLabel.setText(person.getState());
			phoneNumberLabel.setText(person.getPhoneNumber());
		} else {
			// Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			phoneNumberLabel.setText("");
			memberIdLabel.setText("");
			stateLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			String memberId = personTable.getItems().get(selectedIndex).getMemberId();
			this.memberDao.delete(memberId);
			personTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(application.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		MemberDto tempPerson = null;// new Person();
		if (libraryUiManager.showMemberEditDialog(tempPerson)) {
			personTable.setItems(FXCollections.observableArrayList(getAllMemberList()));
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		MemberDto selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = libraryUiManager.showMemberEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}

		} else {
			this.fxViewManager.showError(getCurrentStage(), "Please select a person in the table.", "No Selection",
					"No Person Selected");
		}
	}
}
