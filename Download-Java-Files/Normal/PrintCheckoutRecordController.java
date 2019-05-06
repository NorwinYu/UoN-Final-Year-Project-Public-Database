package edu.mum.library.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.common.LibraryException;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseFxModalController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrintCheckoutRecordController extends BaseFxModalController {

	@FXML
	private TextField memberIdField;

	@FXML
	private TextArea consoleText;

	@Autowired
	private LibraryService libraryService;

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			try {
				String all = libraryService.printCheckoutRecord(memberIdField.getText());
				consoleText.setText(all);
				System.out.println(all);
			} catch (LibraryException e) {
				fxViewManager.showInformation(this.getCurrentStage(), e.getMessage(), "Information", "Prompt");
			}
		}
	}

	@FXML
	void clearText() {
		consoleText.setText("");
	}
}
