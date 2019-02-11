package application.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.Input;
import application.Main;
import application.Print;
import application.model.Options;
import application.model.Printer;
import application.model.Router;
import application.model.Website;
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

	private Input in;
	private Options options;
	private Router router;
	private Website website;
	private InfoController info;
	private Print printer;
	private Printer printerObj;
	private Main main;
	
	private List<String> file = new ArrayList<String>();
	private long uptime;
	private boolean started = false;
	private boolean first = true;
	private boolean print = false;
	
	public RootController() {
	}
	
	public void initialize() {
	}
	
	@FXML
	private void handleStart() {
		uptime = System.nanoTime();
		file.add(String.format("START    You started the program at %s", getTime()));
		if(started == false && first == false) {
			addInfo("Resumed...", "greenStyle");
		}
		progress.setVisible(true);
		started = true;
		first = false;
		start.setDisable(true);
		stop.setDisable(false);
		in.start(info);
	}
	
	@FXML
	private void handlePause() {
		started = false;
		List<String> temp = in.stop(info);
		for(String str : temp) {
			file.add(str);
		}
		temp.clear();
		progress.setVisible(false);
		start.setDisable(false);
		stop.setDisable(true);
		file.add(String.format("STOP     You stopped the program at %s", getTime()));
		addInfo("Paused...", "yellowStyle");
	}
	
	@FXML
	private void handleUpdate() {
		if(in.status()) {
			long downtime = System.nanoTime();
			long curTime = downtime - uptime;
			long seconds = TimeUnit.SECONDS.convert(curTime, TimeUnit.NANOSECONDS);
			long minutes = TimeUnit.MINUTES.convert(curTime, TimeUnit.NANOSECONDS);
			long hours = TimeUnit.HOURS.convert(curTime, TimeUnit.NANOSECONDS);
			String str = String.format("%s:%s:%s Time has passed.", Long.toString(hours), Long.toString(minutes), Long.toString(seconds));
			file.add( "UPDATE   " + str);
			info.addToInfo(str,"greenStyle");
		} if(in.status() == false || started == false) {
			addInfo("Nothing is running.", "yellowStyle");
		}
	}
	
	@FXML
	private void handleClear() {
		info.clearInfo();
	}
	
	@FXML
	private void handleOptions() {
		boolean isOk = main.showOptions();
		if(isOk) {
			Options opt = main.getOptions();
			options.setNumber(opt.getNumber());
			options.setSize(opt.getSize());
			options.setPing(opt.getPing());
		}
	}
	
	@FXML
	private void handleWebsite() {
		String websiteAddress = main.showWebsite();
		website.setWebsite(websiteAddress);
		if(routerItem.isSelected() && !print)
			addInfo("Changed to Website", "blueStyle");
		websiteItem.setSelected(true);
		routerItem.setSelected(false);
		print = false;
	}
	
	@FXML
	private void handleRouter() {
		String routerIP = main.showRouter();
		router.setRouter(routerIP);
		if(websiteItem.isSelected() && !print)
			addInfo("Changed to Router", "blueStyle");
		websiteItem.setSelected(false);
		routerItem.setSelected(true);
		print = false;
	}
	
	@FXML
	private void handlePrint() {
		boolean isOk = main.showPrint();
		if(isOk) {
			if(!file.isEmpty()) {
				List<String> temp = in.getFile();
				for(String str : temp) {
					file.add(str);
				}
				temp.clear();
				printer.print(printerObj.gethtml(), file, getTime(), getDate());
			}
		}
	}
	
	@FXML
	private void handleExit() {
		Boolean isOk = main.showExit();
		if(isOk != null) {
			if(isOk) {
				List<String> temp = in.getFile();
				for(String str : temp) {
					file.add(str);
				}
				file.add(String.format("END      You ended the program at   %s", getTime()));
				printer.print(printerObj.gethtml(), file, getTime(), getDate());
				System.exit(0);
			}
			if(isOk == false)
				System.exit(0);
		}
	}
	
	@FXML
	private void handleHelp() {
		main.showHelp();
	}
	
	private String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.now();
		return dtf.format(now);
	}
	
	private String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
		LocalDate now = LocalDate.now();
		return dtf.format(now);
	}
	
	public void updateUI(String str, String style) {
		info = main.getInfoController();
		info.addToInfo(str, style);
	}
	
	public boolean getStarted() {
		return started;
	}
	
	public void setMain(Main main) {
		this.main = main;
		in = main.getInput();
		options = main.getOptions();
		website = main.getWebsite();
		router = main.getRouter();
		printer = main.getPrinter();
		printerObj = main.getPrinterObject();
		
		setListeners();
	}
	
	private void setListeners() {
		options.numberProperty().addListener((obs,ov,nv) -> {
			in.setPingNumber(options.getNumber());
			addInfo("Number of Pings set to: " + in.getPingNumber(), "blueStyle");
			print = true;
		});
		options.sizeProperty().addListener((obs,ov,nv) -> {
			in.setPingSize(options.getSize());
			addInfo("Size of Packets set to: " + in.getPingSize(), "blueStyle");
			print = true;
		});
		options.pingProperty().addListener((obs,ov,nv) -> {
			in.setTimeOut(options.getPing());
			addInfo("Time Between Pings set to: " + in.getTimeOut(), "blueStyle");
			print = true;
		});
		website.websiteProperty().addListener((obs,ov,nv) -> {
			in.setWebsite(website.getWebsite());
			addInfo("Website changed to: " + in.getWebsite(), "blueStyle");
			print = true;
		});
		router.routerProperty().addListener((obs,ov,nv) -> {
			in.setRouterIP(router.getRouter());
			addInfo("Router changed to: " + in.getRouter(), "blueStyle");
			print = true;
		});
	}
	
	private void addInfo(String str, String style) {
		info = main.getInfoController();
		info.addToInfo(str, style);
	}
}
