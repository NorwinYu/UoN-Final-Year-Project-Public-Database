package edu.mum.library.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.mock.CreateMockData;
import edu.mum.library.model.AuthorizationLevel;
import edu.mum.library.service.SessionManager;
import edu.mum.library.view.base.BaseFxController;
import edu.mum.library.view.util.LibraryUiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RootController extends BaseFxController {

	@FXML
	private Menu adminMenu;
	@FXML
	private Menu librarianMenu;

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private LibraryUiManager libraryUiManager;

	@FXML
	void logout() {
		adminMenu.setVisible(true);
		librarianMenu.setVisible(true);

		sessionManager.logout();
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(null);
		if (libraryUiManager.showLoginDialog()) {
			menuInit();
		} else {
			Platform.exit();
		}
	}

	@FXML
	void shutdown() {
		Platform.exit();
	}

	public void menuInit() {
		AuthorizationLevel authorizationLevel = sessionManager.getLoginUser().getAuthorizationLevel();
		if (authorizationLevel == AuthorizationLevel.ADMIN) {
			librarianMenu.setVisible(false);
		} else if (authorizationLevel == AuthorizationLevel.LIBRARIAN) {
			adminMenu.setVisible(false);
		}
	}

	public void showMemberUi() {
		AnchorPane personOverview = this.application.importLayout("/edu/mum/library/view/MemberOverview.fxml");
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(personOverview);
	}

	public void showBookUi() {
		AnchorPane view = this.application.importLayout("/edu/mum/library/view/BookOverview.fxml");
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(view);
	}

	public void showCheckoutUi() {
		AnchorPane view = this.application.importLayout("/edu/mum/library/view/CheckoutDialog.fxml");
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(view);
	}

	public void showPrintCheckoutRecordUi() {
		//
		AnchorPane view = this.application.importLayout("/edu/mum/library/view/PrintCheckoutDialog.fxml");
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(view);
	}

	public void showOverduesUi() {
		AnchorPane view = this.application.importLayout("/edu/mum/library/view/OverduesOverview.fxml");
		((BorderPane) this.getCurrentStage().getScene().getRoot()).setCenter(view);

	}

	@Autowired
	CreateMockData createMockData;

	@FXML
	void createMockData() {
		createMockData.create();
	}
}
