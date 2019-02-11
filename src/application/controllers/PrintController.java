package application.controllers;

import application.model.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class PrintController {
	
	@FXML
	private Button yes;
	@FXML
	private CheckBox html;
	
	private Stage printStage;
	private boolean ok = false;
	private boolean htmlBool;
	private Printer printer;
	
	@FXML
	private void handleHTML() {
		if(html.isSelected())
			printer.sethtml(true);
		else
			printer.sethtml(false);
	}
	
	@FXML
	private void handleYes() {
		ok = true;
		printStage.close();
	}
	
	public void setStage(Stage printStage) {
		this.printStage = printStage;
		this.printer = new Printer();
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
