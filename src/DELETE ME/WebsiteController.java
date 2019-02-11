package application.controllers;

import org.apache.commons.validator.routines.UrlValidator;

import application.model.Website;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WebsiteController {
	/*@FXML
	private Website website;
	@FXML
	private TextField websiteField;
	@FXML
	private Button ok;
	
	private Stage websiteStage;
	
	public WebsiteController() {
	}
	
	@FXML
	public void initialize() {
		websiteField.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
	}
	
	@FXML
	private void handleOk() {
		UrlValidator valid = new UrlValidator();
		String url = websiteField.getText();
		if(!url.contains("http://"))
			url = "http://" + url;
		if(valid.isValid(url)) {
			website.setWebsite(websiteField.getText());
			websiteStage.close();
		}
		else {
			toggleWarning(websiteField,true);
		}
	}
	
	private void toggleWarning(TextField field, boolean on) {
		if(on)
			field.getStyleClass().add("warningStyle");
		else
			field.getStyleClass().remove("warningStyle");
	}
	
	public String getWebsite() {
		return website.getWebsite();
	}
	
	public void enter() {
		handleOk();
	}
	
	public void setWebsite(Website website, Stage websiteStage) {
		this.website = website;
		this.websiteStage = websiteStage;
		
		websiteField.setText(website.getWebsite());
	}*/
}
