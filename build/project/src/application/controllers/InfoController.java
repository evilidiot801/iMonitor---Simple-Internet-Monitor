package application.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoController {
	@FXML
	private VBox infoBox;
	
	public InfoController() {
	}
	
	@FXML
	public void initialize() {
	}
	
	public void addToInfo(String txt, String style) {
		Text text = new Text(txt);
		BorderPane textArea = new BorderPane(text);
		textArea.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
		textArea.getStyleClass().addAll(style,"infoStyle");
		BorderPane.setAlignment(text, Pos.CENTER);
		infoBox.getChildren().add(textArea);
	}
	
	public void clearInfo() {
		infoBox.getChildren().clear();
	}
}
