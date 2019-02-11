package application.controllers.Ping;

import org.apache.commons.validator.routines.UrlValidator;

import application.Main;
import application.controllers.InfoController;
import application.model.PingOptions;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WebsiteController {
	@FXML
	private GridPane websitePane;
	@FXML
	private TextField website;
	@FXML
	private Label warning;
	@FXML
	private Button okWebsite;
	
	private Stage stage;
	private PingOptions options;
	
	@FXML
	private void handleWebsite() {
		UrlValidator valid = new UrlValidator();
		String url = website.getText();
		if(!url.contains("http://"))
			url = "http://" + url;
		if(valid.isValid(url)) {
			options.setWebsite(website.getText());
			options.setWeb(true);
			stage.close();
		}
		else {
			toggleWarning(website,true);
		}
	}
	
	public void enterWebsite() {
		handleWebsite();
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
		
		if(website != null)
			website.setText(options.getWebsite());
	}
}
