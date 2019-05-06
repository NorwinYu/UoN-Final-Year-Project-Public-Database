package edu.mum.library.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.common.LibraryException;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseFxModalController;
import edu.mum.library.view.util.LibraryUiManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CheckoutBookDialogController extends BaseFxModalController {

	@FXML
	private TextField isbnField;
	@FXML
	private TextField memberIdField;

	@Autowired
	private LibraryService libraryService;
	@Autowired
	private LibraryUiManager libraryUiManager;

	/**
	 * Called when the user clicks checkout.
	 */
	@FXML
	private void checkout() {
		if (isInputValid()) {

			try {
				libraryService.checkoutBook(memberIdField.getText(), isbnField.getText());

				libraryUiManager.showCheckoutOverviewDialog(memberIdField.getText());

			} catch (LibraryException e) {
				fxViewManager.showError(this.getCurrentStage(), e.getMessage(), "Information", "Warning");
			}

			return;
		}
	}

	@Override
	public void postInit() {
		this.registerRequired("Member Id", memberIdField::getText);
		this.registerRequired("ISBN", isbnField::getText);
	}
}
