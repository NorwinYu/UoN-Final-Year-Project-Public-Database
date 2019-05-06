package edu.mum.library;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import edu.mum.library.view.RootController;
import edu.mum.library.view.base.BaseFxController;
import edu.mum.library.view.util.LibraryUiManager;
import edu.mum.library.view.util.UserObjectForView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * An Application Main Entry
 *
 * @author dongwang
 *
 */
@SpringBootApplication
public class LibraryApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private ConfigurableApplicationContext context;

	private Stage primaryStage;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public <T extends Parent> T importLayout(String resourcePath) {
		return importLayout(resourcePath, null, null);
	}

	public <T extends Parent> T importLayout(String resourcePath, Stage stage, Object param) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
		loader.setControllerFactory(context::getBean);
		try {

			T result = loader.load();
			if (stage != null) {
				BaseFxController controller = (BaseFxController) loader.getController();
				stage.setUserData(new UserObjectForView(controller, param));
				controller.setCurrentStage(stage);
				stage.setOnHidden(e -> controller.onWindowClose());
				controller.postInit();
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Failed to load resource!", e);
		}
	}

	@Override
	public void init() throws Exception {
		// Init the SprintApplication Context
		SpringApplicationBuilder builder = new SpringApplicationBuilder(LibraryApplication.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));
		context.getBean(LibraryApplication.class).context = context;
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane rootLayout = importLayout("/edu/mum/library/view/RootLayout.fxml", primaryStage, null);

		this.primaryStage = primaryStage;
		context.getBean(LibraryApplication.class).primaryStage = this.primaryStage;
		this.primaryStage.setTitle("AddressApp");
		this.primaryStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();

		LibraryUiManager uiManager = context.getBean(LibraryUiManager.class);
		if (!uiManager.showLoginDialog()) {
			Platform.exit();
		} else {
			((RootController) UserObjectForView.getControllerFromStage(primaryStage)).menuInit();
		}

	}

	@Override
	public void stop() throws Exception {
		context.close();
	}
}
