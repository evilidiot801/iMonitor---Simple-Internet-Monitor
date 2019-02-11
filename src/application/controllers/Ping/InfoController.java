package application.controllers.Ping;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import application.Main;
import application.Print;
import application.Tasks;
import application.model.PingOptions;
import application.model.Printer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InfoController {
	@FXML
	private VBox infoBox;
	@FXML
	private ProgressIndicator progress;
	@FXML
	private ToggleGroup startStop;
	@FXML
	private ToggleButton start;
	@FXML
	private ToggleButton stop;
	@FXML
	private Label average;
	@FXML
	private Label highest;
	@FXML
	private Label isWeb;
	@FXML
	private Label conn;

	private int lastms;
	private int lastping;
	private Print printer;
	private Printer printOptions;
	private int seconds;
	private int minutes;
	private int hours;
	
	private List<String> file = new ArrayList<String>();
	private long uptime;
	private boolean print = false;
	private String down;
	private int increment;
	private boolean log = true;
	
	private Main main;
	private Tasks ping;
	private PingOptions options;

	private BooleanProperty started;
	private StringProperty time;
	
	public InfoController() {
	}
	
	@FXML
	public void initialize() {
		started = new SimpleBooleanProperty(true);
		time = new SimpleStringProperty("");
	}
	
	@FXML
	private void handleStart() throws Exception {
		file.add(String.format("START    You started the program at %s", getTime()));
		if(uptime != 0) {
			addToInfo("Resumed...", "greenStyle");
		}
		uptime = System.nanoTime();
		progress.setVisible(true);
		start.setDisable(true);
		stop.setDisable(false);
		started.set(true);
		ping.restart();
		//timer.restart();
	}
	
	@FXML
	private void handlePause() throws InterruptedException {
		started.set(false);
	
		progress.setVisible(false);
		start.setDisable(false);
		stop.setDisable(true);
		
		file.add(String.format("STOP     You stopped the program at %s", getTime()));
		addToInfo("Paused...", "yellowStyle");
		ping.cancel();
		//timer.cancel();
	}
	
	@FXML
	private void handleClear() {
		infoBox.getChildren().clear();
	}
	
	public void addToInfo(String txt, String style) {
		if(txt != null) {
			Text text = new Text(txt);
			BorderPane textArea = new BorderPane(text);
			textArea.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			textArea.getStyleClass().addAll(style,"infoStyle");
			BorderPane.setAlignment(text, Pos.CENTER);
			infoBox.getChildren().add(textArea);
		}
	}
	
	public void infoUpdate() {
		if(started.get()) {
			long downtime = System.nanoTime();
			long curTime = downtime - uptime;
			long seconds = TimeUnit.SECONDS.convert(curTime, TimeUnit.NANOSECONDS);
			long minutes = TimeUnit.MINUTES.convert(curTime, TimeUnit.NANOSECONDS);
			long hours = TimeUnit.HOURS.convert(curTime, TimeUnit.NANOSECONDS);
			String str = String.format("%s:%s:%s Time has passed.", Long.toString(hours), Long.toString(minutes), Long.toString(seconds));
			file.add( "UPDATE   " + str);
			addToInfo(str,"greenStyle");
		} else {
			addToInfo("Nothing is running.", "yellowStyle");
		}
	}
	
	private int old = 1;
	public boolean compareSize(int index) {
		if(old < index) {
			old = infoBox.getChildren().size();
			return true;
		}else {
			old = infoBox.getChildren().size();
			return false;
		}
	}

	private void setServices() {
		ping.setPeriod(new Duration(Long.parseLong(options.getPing())*1000));
		ping.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				if(infoBox.getChildren().size()>0) {
					BorderPane temp = (BorderPane)infoBox.getChildren().get(infoBox.getChildren().size()-1);
					Text temptext = (Text)temp.getChildren().get(0);
					if(temptext.getText().contains("Ping Result"))
						infoBox.getChildren().remove(temp);
				}
				String conn = "";
				String ms = "";
				String loss = "";
				String output = e.getSource().getValue().toString();
				conn = output.substring(0, output.indexOf("."));
				if(output.contains("Connected")) {					
					if(log) {
						addToInfo(conn,"greenStyle");
						//logger(true);
						log = false;
					}
					
				}
				if(output.contains("Lost")) {
					if(log == false) {
						notification();
						output = output.replace(".null.null", "");
						addToInfo(output,"redStyle");
						//logger(false);
						log = true;
					}
				}
				if(log == false) {
					loss = output.substring(output.lastIndexOf("."));
					loss = loss.replace(".", "");
					ms = output.substring(output.indexOf("."),output.lastIndexOf("."));
					ms = ms.replace(".", "");
					file.add("Ping Results: " + loss + "% Loss : Average = " + ms + "ms.");
					String style = "";
					if(Integer.parseInt(ms) < 50)
						style = "greenStyle";
					if(Integer.parseInt(ms) > 50 && Integer.parseInt(ms) < 150)
						style = "yellowStyle";
					if(Integer.parseInt(ms) > 150)
						style = "redStyle";
					addToInfo("Ping Results: " + loss + "% Loss : Average = " + ms + "ms.", style);
					lastping++;
					lastms += Integer.parseInt(ms);
					average.setText(Integer.toString(lastms/lastping));
					if(Integer.parseInt(highest.getText()) < Integer.parseInt(ms)) {
						highest.setText(ms);
					}
				}
				ping.setPeriod(new Duration(Long.parseLong(options.getPing())*1000));
			}
		});
		ping.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				System.out.println("Failed");
			}
		});
	}
	
	private void logger(boolean c) {			
		if(c) {
			file.add("         Down for " + downTime());
			file.add("");
			file.add(String.format("UP       %s : You got connection at  %s", ++increment, getTime()));
		}
		else {
			down = getTime();
			file.add("");
			file.add(String.format("DOWN     %s : You lost connection at %s", ++increment, getTime()));
		}
	}
	
	private String downTime() {
		String up = getTime();
		up = up.replaceAll(":", "");
		down = down.replaceAll(":", "");
		int intup = Integer.parseInt(up);
		int intdown = Integer.parseInt(down);
		return Integer.toString(intup - intdown);
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
	
	private void notification() {
		SystemTray tray = SystemTray.getSystemTray();
		Image img = Toolkit.getDefaultToolkit().createImage("info.png");
		TrayIcon trayicon = new TrayIcon(img, "Lost Connection");
		trayicon.setImageAutoSize(true);
		try {
			tray.add(trayicon);
			trayicon.displayMessage("Lost Connection", "Connection was lost at " + time.get(), MessageType.INFO);
		} catch(AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void printFile() {
		if(!file.isEmpty()) {
			printer.print(printOptions.gethtml(), file, getTime(), getDate(), null);
		}
	}
	
	public void clearInfo() {
		infoBox.getChildren().clear();
	}
	
	public List<String> getFile() {
		System.out.print(file);
		return file;
	}
	
	public void setWeb(boolean web) {
		options.setWeb(web);
	}
	
	public void setMain(Main main) {
		this.main = main;
		ping = new Tasks(options);
		printer = main.getPrinter();
		printOptions = main.getPrinterObject();
		setServices();
	}
	
	public void createPingOptions(String number, String size, String ping, String warning, String web, String rout, boolean isWeb) {
		options = new PingOptions(number, size, ping, warning, web, rout, isWeb);
		if(isWeb) {
			this.isWeb.setText("Website : ");
			this.conn.setText(web);
		}else {
			this.isWeb.setText("Router : ");
			this.conn.setText(rout);
		}
		addlisteners();
	}
	
	private void addlisteners() {
		options.webProperty().addListener((obs,ov,nv) -> {
			if(nv) {
				addToInfo("Changed to Website", "blueStyle");
				isWeb.setText("Website : " + options.getWebsite());
			}else {
				addToInfo("Changed to Router", "blueStyle");
				isWeb.setText("Router : " + options.getRouter());
			}
		});
		options.routerProperty().addListener((obs,ov,nv) -> {
			addToInfo("Router changed to: " + nv, "blueStyle");
			conn.setText(nv);
		});
		options.websiteProperty().addListener((obs,ov,nv) -> {
			addToInfo("Website changed to: " + nv, "blueStyle");
			conn.setText(nv);
		});
		options.numberProperty().addListener((obs,ov,nv) -> {
			addToInfo("Number of Pings set to: " + nv, "blueStyle");
		});
		options.sizeProperty().addListener((obs,ov,nv) -> {
			addToInfo("Size of Packets set to: " + nv, "blueStyle");
		});
		options.pingProperty().addListener((obs,ov,nv) -> {
			addToInfo("Time Between Pings set to: " + nv, "blueStyle");
		});
	}
	
	public PingOptions getOptions() {
		return options;
	}
}
