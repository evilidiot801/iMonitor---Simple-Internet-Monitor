package application.controllers;

import application.model.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RouterController {
	@FXML
	private TextField router;
	@FXML
	private Button ok;
	
	private Stage routerStage;
	private Router rout;
	
	public RouterController() {
	}
	
	@FXML
	public void initialize() {
		router.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
	}
	
	@FXML
	private boolean handleOk() {
		String regex = "\\d+(\\.\\d+)*";
		if(router.getText().matches(regex)) {
			rout.setRouter(router.getText());
			routerStage.close();
			return true;
		}
		else {
			toggleWarning(router);
			return false;
		}
	}
	
	private void toggleWarning(TextField field) {
		field.getStyleClass().add("warningStyle");
	}
	
	public void enter() {
		handleOk();
	}
	
	public void setRouter(String router, Stage routerStage, Router rout) {
		this.router.setText(router);
		this.routerStage = routerStage;
		this.rout = rout;
	}
	
	public String getRouter() {
		return rout.getRouter();
	}
}
