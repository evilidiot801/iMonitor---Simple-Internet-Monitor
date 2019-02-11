package application.controllers;

import application.model.Options;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OptionsController {
	/*@FXML
	private GridPane optionsPane;
	@FXML
	private TextField number;
	@FXML
	private TextField size;
	@FXML
	private TextField ping;
	@FXML
	private Label warning;
	@FXML
	private StackPane warningBox;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	
	private Stage optionsStage;
	private Options options;
	
	public OptionsController() {
	}
	
	@FXML
	public void initialize() {
		number.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
		size.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
		ping.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
	}
	
	@FXML
	private void handleCancel() {
		optionsStage.close();
	}
	
	@FXML
	private void handleOk() {
		String warningText = "";
		String regex = "\\d+";
		TextField[] fields = new TextField[3];
		int[][] constraints = new int[3][2];
		String[] warnings = new String[3];
		boolean[] checks = new boolean[3];
		fields[0] = number;
		fields[1] = size;
		fields[2] = ping;
		constraints[0][0] = 1;
		constraints[0][1] = 1000;
		constraints[1][0] = 32;
		constraints[1][1] = 1500;
		constraints[2][0] = 1;
		constraints[2][1] = 20;
		warnings[0] = "Number of Pings must be between 1 and 20.\n";
		warnings[1] = "Size of Packet must be between 32 and 1500.\n";
		warnings[2] = "Time must be above 0.\n";
		for(int i=0; i<3; i++) {
			if(fields[i].getText().matches(regex)) {
				if(Integer.parseInt(fields[i].getText()) < constraints[i][0] || 
						Integer.parseInt(fields[i].getText()) > constraints[i][1]) {
					toggleWarning(fields[i],true);
					warningText += warnings[i];
					checks[i] = false;
				}
				else {
					toggleWarning(fields[i],false);
					checks[i] = true;
				}
			}
			else {
				toggleWarning(fields[i],true);
				warningText += "Invalid argument in the " + fields[i].getId().replace("options", "") + " feild.\n";
				checks[i] = false;
			}
		}
		boolean ok = true;
		for(int i=0; i<3; i++) {
			if(checks[i] == false)
				ok = false;
		}
		if(ok) {
			options.setNumber(number.getText());
			options.setSize(size.getText());
			options.setPing(ping.getText());
			optionsStage.close();
		}
		warning.setText(warningText);
	}
	
	private void toggleWarning(TextField field, boolean on) {
		if(on)
			field.getStyleClass().add("warningStyle");
		else
			field.getStyleClass().remove("warningStyle");
	}
	
	public void setStage(Stage stage) {
		optionsStage = stage;
	}
	
	public void setOptions(Options options, Main main) {
		this.options = options;
		
		ping.setText(options.getPing());
		number.setText(options.getNumber());
		size.setText(options.getSize());
		warning.setText(options.getWarning());
	}
	
	public void enter() {
		handleOk();
	}*/
}
