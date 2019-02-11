package application.controllers;

import application.Main;
import application.NetTasks;
import application.Print;
import application.controllers.Net.NetTabController;
import application.controllers.Ping.PingTabController;
import application.model.PingOptions;
import application.model.Printer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class RootController {
	@FXML
	private CheckMenuItem websiteItem;
	@FXML
	private CheckMenuItem routerItem;
	@FXML
	private ProgressIndicator progress;
	@FXML
	private ToggleGroup startStop;
	@FXML
	private ToggleButton start;
	@FXML
	private ToggleButton stop;
	@FXML
	private CheckMenuItem admin;
	
	private PingOptions options;
	private Main main;
	private StringProperty adminString = new SimpleStringProperty("-a");
	
	private boolean started = false;
	
	@FXML
	private void handleUpdate() {
		//info = main.getPingController();
		//info.infoUpdate();
	}
	
	@FXML
	private void handleOptions() {
		main.showOptions();
	}
	
	@FXML
	private void handleWebsite() {
		options = main.getPingOptions();
		main.showWebsite();
		websiteItem.setSelected(true);
		routerItem.setSelected(false);
		options.setWeb(true);
	}
	
	@FXML
	private void handleRouter() {
		options = main.getPingOptions();
		main.showRouter();
		websiteItem.setSelected(false);
		routerItem.setSelected(true);
		options.setWeb(false);
	}
	
	@FXML
	private void handlePrint() {
		boolean isOk = main.showPrint();
		if(isOk) {
			justPrint();
		}
	}
	
	@FXML
	private void handleExit() {
		main.showExit();
		NetTabController controller = main.getNetController();
		NetTasks task = controller.getTask();
		task.setOn(false);
		System.exit(0);
	}
	
	public void justPrint() {
		Printer options = main.getPrinterObject();
		Print printer = main.getPrinter();
		PingTabController controller = main.getPingController();
		NetTabController nController = main.getNetController();
		printer.print(options.gethtml(), controller.getFile(), controller.getTime(), controller.getDate(), nController.getList());
	}
	
	@FXML
	private void handleHelp() {
		main.showHelp();
	}
	
	@FXML
	private void handleAdmin() {
		if(admin.isSelected()) {
			adminString.set("-b");
		}
		else {
			adminString.set("-a");
		}
		System.out.println(adminString);
	}
	
	public String getAdmin() {
		return adminString.get();
	}
	
	public boolean getStarted() {
		return started;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
