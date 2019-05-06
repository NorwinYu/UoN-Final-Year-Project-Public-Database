package edu.mum.library.view.util;

import javafx.stage.Stage;

public class UserObjectForView {
	private Object controller;
	private Object parameter;

	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public UserObjectForView(Object controller, Object parameter) {
		super();
		this.controller = controller;
		this.parameter = parameter;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	public static UserObjectForView getUserObjectForViewFromStage(Stage stage) {
		return ((UserObjectForView) stage.getUserData());
	}

	public static Object getControllerFromStage(Stage stage) {
		return getUserObjectForViewFromStage(stage).getController();
	}
	public static Object getParamFromStage(Stage stage) {
		return getUserObjectForViewFromStage(stage).getParameter();
	}
}
