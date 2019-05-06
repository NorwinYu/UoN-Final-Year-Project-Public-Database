package edu.mum.library.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.common.LibraryException;
import edu.mum.library.dataaccess.MemberDao;
import edu.mum.library.model.Address;
import edu.mum.library.model.Member;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseLibraryFxModalEditController;
import edu.mum.library.view.dto.MemberDto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MemberEditDialogController extends BaseLibraryFxModalEditController<MemberDto> {

	@FXML
	private TextField cityField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField memberIdField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField zipcodeField;

	@Autowired
	private LibraryService libraryService;
	@Autowired
	private MemberDao memberDao;

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		try {
			if (isInputValid()) {
				if (entityDto != null) {
					// person.setMemberId(memberIdField.getText());
					fromViewToDto();
					// Read from database to update
					Member memeber = memberDao.readById(entityDto.getMemberId());

					memeber.setFirstName(firstNameField.getText());
					memeber.setLastName(lastNameField.getText());
					memeber.getAddress().setStreet(streetField.getText());
					memeber.getAddress().setZipcode(zipcodeField.getText());
					memeber.getAddress().setCity(cityField.getText());
					memeber.setPhoneNumber(phoneNumberField.getText());
					memeber.getAddress().setState(stateField.getText());
					memberDao.save(memeber);

				} else {
					Member member = new Member(memberIdField.getText(), firstNameField.getText(),
							lastNameField.getText(), phoneNumberField.getText());
					member.setPersonAddress(new Address(streetField.getText(), cityField.getText(),
							stateField.getText(), zipcodeField.getText()));
					libraryService.addMember(member);

				}
				okClicked = true;
				this.getCurrentStage().close();
			}
		} catch (LibraryException ex) {
			this.fxViewManager.showError(getCurrentStage(), ex.getMessage(), "Member Management",
					"Please correct Error");
		}
	}

	@Override
	protected String moreCheck() {
		if (!StringUtils.isEmpty(zipcodeField.getText())) {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(zipcodeField.getText());
			} catch (NumberFormatException e) {
				return "No valid postal code (must be an integer)!\n";
			}
		}
		return "";
	}

	@Override
	protected void postInitInChild() {
		if (entityDto != null) {
			memberIdField.setDisable(true);
		}
		this.registerRequired("Member Id", memberIdField::getText);
		this.registerRequired("First Name", firstNameField::getText);
		this.registerRequired("Last Name", lastNameField::getText);
	}

}
