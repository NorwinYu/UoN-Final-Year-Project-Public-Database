package edu.mum.library.view.util;

import org.springframework.stereotype.Component;

import edu.mum.library.view.base.BaseFxModalController;
import edu.mum.library.view.base.BaseUIController;
import edu.mum.library.view.dto.BookDto;
import edu.mum.library.view.dto.MemberDto;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class LibraryUiManager extends BaseUIController {

	public BaseFxModalController showOverduesDialog() {

		return showDialogReturnController("/edu/mum/library/view/OverduesOverview.fxml", "Overdues", null);
	}
	public BaseFxModalController showPrintDialogDialog() {

		return showDialogReturnController("/edu/mum/library/view/PrintCheckoutDialog.fxml", "Print", null);
	}
	public BaseFxModalController showAuthorEditDialogDialog() {

		return showDialogReturnController("/edu/mum/library/view/AuthorEditDialog.fxml", "Add Author", null);
	}

	public boolean showCheckoutOverviewDialog(String memberId) {

		return showDialog("/edu/mum/library/view/CheckoutRecordOverview.fxml", "Checkout Record", memberId);
	}

	/**
	 * Show a MemberDto Dialog
	 *
	 * @param person
	 * @return
	 */
	public boolean showMemberEditDialog(MemberDto person) {

		return showDialog("/edu/mum/library/view/MemberEditDialog.fxml", "Edit Book", person);
	}

	/**
	 * Show a BookDto Dialog
	 *
	 * @param person
	 * @return
	 */
	public boolean showBookEditDialog(BookDto person) {

		return showDialog("/edu/mum/library/view/BookEditDialog.fxml", "Edit Book", person);
	}

	/**
	 * Show a Login Dialog
	 *
	 * @param person
	 * @return
	 */
	public boolean showLoginDialog() {

		return showDialog("/edu/mum/library/view/LoginDialog.fxml", "Login", null);

	}

	private boolean showDialog(String layoutPath, String title, Object param) {
		BaseFxModalController controller = showDialogReturnController(layoutPath, title, param);
		return controller.isOkClicked();
	}

	private BaseFxModalController showDialogReturnController(String layoutPath, String title, Object param) {
		Stage dialogStage = new Stage();
		// Load the fxml file and create a new stage for the popup dialog.
		AnchorPane page = this.application.importLayout(layoutPath, dialogStage, param);

		dialogStage.setTitle(title);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(this.application.getPrimaryStage());
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
		BaseFxModalController controller = (BaseFxModalController) UserObjectForView
				.getControllerFromStage(dialogStage);
		return controller;
	}
}
