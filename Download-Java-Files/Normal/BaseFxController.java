package edu.mum.library.view.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.mum.library.view.util.FxViewManager;
import edu.mum.library.view.util.UserObjectForView;
import javafx.stage.Stage;

public abstract class BaseFxController extends BaseUIController {

	protected static class CheckField {
		private CheckType checkType;

		private String name;

		private Supplier<String> supplier;
		protected CheckField(String name, Supplier<String> supplier) {
			this(name, supplier, CheckType.REQUIRED);
		}

		protected CheckField(String name, Supplier<String> supplier, CheckType checkType) {
			this.supplier = supplier;
			this.name = name;
			this.checkType = checkType;
		}

		public CheckType getCheckType() {
			return checkType;
		}

		public String getName() {
			return name;
		}

		public Supplier<String> getSupplier() {
			return supplier;
		}
	}

	protected static enum CheckType {
		REQUIRED(new RequiredCheck());
		private ICheck check;

		CheckType(ICheck check) {
			this.check = check;
		}

		protected String check(CheckField val) {
			if (!check.check(val)) {
				return check.formatErrorMessage(val.name);

			}
			return "";

		}
	}

	protected static interface ICheck {
		boolean check(CheckField val);

		String formatErrorMessage(String name);
	}

	private static class RequiredCheck implements ICheck {

		@Override
		public boolean check(CheckField val) {
			return !StringUtils.isEmpty(val.getSupplier().get());
		}

		@Override
		public String formatErrorMessage(String name) {
			return "No valid " + name + "!\n";
		}
	}

	protected List<CheckField> checkFieldList = new ArrayList<>();

	protected Object closeResult;

	protected Stage currentStage;

	@Autowired
	protected FxViewManager fxViewManager;

	protected Object returnResult;

	public void closeWindow() {
		this.getCurrentStage().close();
	}

	public Object getCloseResult() {
		return closeResult;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public Object getReturnResult() {
		return returnResult;
	}
	
	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	protected boolean isInputValid() {
		String errorMessage = "";

		for (CheckField checkField : checkFieldList) {
			errorMessage += checkField.getCheckType().check(checkField);

		}
		errorMessage += moreCheck();
		if (errorMessage.length() == 0) {
			return true;
		} else {
			fxViewManager.showError(this.getCurrentStage(), errorMessage, "Invalid Fields",
					"Please correct invalid fields");

			return false;
		}
	}

	protected String moreCheck() {
		return "";
	}

	public void onWindowClose() {

		this.getCurrentStage().setUserData(new UserObjectForView(this, null));

	}

	public void postInit() {

	}

	protected void registerRequired(String name, Supplier<String> supplier) {
		checkFieldList.add(new CheckField(name, supplier));
	}

	public void setCloseResult(Object closeResult) {
		this.closeResult = closeResult;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	protected void setReturnResult(Object returnResult) {
		this.returnResult = returnResult;
	}
}
