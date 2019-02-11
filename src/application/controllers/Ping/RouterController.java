package application.controllers.Ping;

import application.Main;
import application.controllers.InfoController;
import application.model.PingOptions;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RouterController {
	@FXML
	private GridPane routerPane;
	@FXML
	private TextField router;
	@FXML
	private Label warning;
	@FXML
	private Button okRouter;
	
	private Stage stage;
	private PingOptions options;
	
	@FXML
	private void handleRouter() {
		String regex = "\\d+(\\.\\d+)*";
		if(router.getText().matches(regex)) {
			options.setRouter(router.getText());
			options.setWeb(false);
			stage.close();
		}
		else {
			toggleWarning(router,true);
		}
	}
	
	public void enterRouter() {
		handleRouter();
	}
	
	private void toggleWarning(TextField field, boolean on) {
		if(on)
			field.getStyleClass().add("warningStyle");
		else
			field.getStyleClass().remove("warningStyle");
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setOptions(PingOptions options, Main main) {
		this.options = options;

		if(router != null)
			router.setText(options.getRouter());
	}
}
