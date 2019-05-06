package edu.mum.library.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.service.LoginService;
import edu.mum.library.view.base.BaseFxModalController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginController extends BaseFxModalController {

	private boolean isLogin = false;

	@Autowired
	private LoginService loginService;

	@FXML
	private PasswordField password;
	@FXML
	private TextField username;

	@Override
	public boolean isOkClicked() {
		return isLogin;
	}

	public void login() {
		// empty check
		isLogin = loginService.login(username.getText(), password.getText());
		if (isLogin) {
			this.currentStage.close();
		}
		else{
			fxViewManager.showError(this.currentStage, "username and/or password incorrect",
					"Error", "Login Error");
		}
	}
}
