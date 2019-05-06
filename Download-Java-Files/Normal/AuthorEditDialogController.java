package edu.mum.library.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.model.Address;
import edu.mum.library.model.Author;
import edu.mum.library.view.base.BaseLibraryFxModalEditController;
import edu.mum.library.view.dto.AuthorDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AuthorEditDialogController extends BaseLibraryFxModalEditController<AuthorDto> {

	@FXML
	private TextField cityField;
	@FXML
	private TextField credentialsField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextArea shortBioField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField zipcodeField;

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			Author author = new Author(firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText());
			author.setPersonAddress(new Address(streetField.getText(), cityField.getText(), stateField.getText(),
					zipcodeField.getText()));
			author.setCredentials(credentialsField.getText());
			author.setShortBio(shortBioField.getText());
			okClicked = true;
			this.setReturnResult(author);
			this.getCurrentStage().close();
		}
	}

	@Override
	protected String moreCheck() {
		// Check whether it is a number
		if (!StringUtils.isEmpty(zipcodeField.getText())) {
			try {
				// try to parse the postal code into an int.
				Integer.parseInt(zipcodeField.getText());
			} catch (NumberFormatException e) {
				return "No valid postal code (must be an integer)!\n";
			}
		}
		return "";
	}

	@Override
	protected void postInitInChild() {
		// Register the required check
		this.registerRequired("First Name", firstNameField::getText);
		this.registerRequired("Last Name", lastNameField::getText);
	}

}
