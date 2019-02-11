package application.controllers;

import application.Main;
import application.Tasks;
import application.controllers.Ping.PingTabController;
import application.model.PingOptions;

public class InfoController {

	private Main main;
	private Tasks ping;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void createPingOptions(PingOptions options) {
		//PingTabController pingTabController = new PingTabController(options);
	}
}
