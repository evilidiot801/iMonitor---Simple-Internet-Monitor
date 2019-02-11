package application.controllers;

import application.model.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class ExitController {
	
	@FXML
	private Button yes;
	@FXML
	private Button no;
	@FXML
	private CheckBox html;
	
	private Stage exitStage;
	private Boolean ok;
	private boolean htmlBool;
	private Printer printer;
	
	public ExitController() {
	}
	
	@FXML
	public void initialize() {
		ok = null;
	}
	
	@FXML
	private void handleYes() {
		ok = true;
		exitStage.close();
	}
	
	@FXML
	private void handleNo() {
		ok = false;
		exitStage.close();
	}
	
	@FXML
	private void handleHTML() {
		if(html.isSelected())
			printer.sethtml(true);
		else
			printer.sethtml(false);
	}
	
	public void setStage(Stage exitStage, Printer printer) {
		this.exitStage = exitStage;
		this.printer = printer;
		html.setSelected(printer.gethtml());
		htmlBool = html.isSelected();
	}
	
	public Boolean getOk() {
		return ok;
	}
	
	public boolean getHTML() {
		return htmlBool;
	}
}
