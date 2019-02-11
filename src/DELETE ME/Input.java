package application;

import java.util.*;
import java.util.concurrent.Callable;

import application.controllers.InfoController;
import application.controllers.RootController;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Input {	
	/*private Ping ping;
	private NetStat netStat;
	private RootController root;
	private boolean connection = false;
	private List<String> info = new ArrayList<>();
	private boolean router = false;
	private boolean reset = true;
	private int increment = 0;
	private String down;
	private String website;
	private String number;
	private String packet;
	private String rout;
	private Duration duration;
	private ScheduledService<String> service;
	private Callable<String> connectionTask;
	private Callable<String> lossTask;
	private Callable<String> msTask;
	private Runnable netstatRun;
	private List<String> netstatList = new ArrayList<String>();
	private Input in;
	private String r = "";

	public Input() {
		this(null, null, null, null, null);
	}
	
	public Input(String number, String packet, String time, String website, String rout) {
		this.number = number;
		this.packet = packet;
		duration = Duration.seconds(Integer.parseInt(time));
		this.website = website;
		this.rout = rout;
		in = this;
	}
	
	public void initialize() throws IOException {
		createService();
		createTasks();
	}
	
	public final void start(InfoController infoController) {
		down = getTime();
		service.restart();
	}
	
	public final List<String> stop(InfoController infoController) {
		service.cancel();
		return info;
	}
	
	public final boolean status() {
		return service.isRunning();
	}
	
	private void createService() throws IOException {
		ping = new Ping();
		netStat = new NetStat();
		
		service = new ScheduledService<String>() {
			protected Task<String> createTask() {
				return new Task<String>() {
					protected String call() {
						return ping.start(connection,router,website,rout,number,packet);
					}
				};
			}
		};
		service.setPeriod(duration);
		
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e){
				r = e.getSource().getValue().toString();
				String conn = "";
				String ms = "";
				String loss = "";
				try {
					conn = connectionTask.call();
					ms = msTask.call();
					loss = lossTask.call();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
				connection = ping.getConnection();
				if(reset == false)
					logger(connection);
				reset = true;
				if(root.getStarted()) {
					if(e.getSource().getValue().toString().contains("C"))
						root.updateUI(conn,"greenStyle");
					System.out.println(".");
				} else {
					service.cancel();
				}
				info.add("Ping Results: " + loss + " Loss : Average = " + ms + "ms.");
			}
		});
		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				if(reset) {
					connection = false;
					notification();
					ping.setConnection(connection);
					root.updateUI("Lost Connection!","redStyle");
					logger(connection);
					reset = false;
				}
			}
		});
	}
	
	private void createTasks(){
		connectionTask = new Callable<String>() {
			@Override
			public String call() {
				String conn = r.substring(0,r.indexOf("."));
				return conn;
			}
		};
		lossTask = new Callable<String>() {
			@Override
			public String call() {
				String loss = r.substring(r.lastIndexOf("."));
				loss = loss.replace(".", "");
				return loss;
			}
		};
		msTask = new Callable<String>() {
			@Override
			public String call() {
				String ms = r.substring(r.indexOf("."));
				ms = ms.replaceAll("\\d+\\%", "");
				ms = ms.replaceAll("\\.","");
				return ms;
			}
		};
	}
	
	private void logger(boolean c) {			
		if(c) {
			info.add("         Down for " + downTime());
			info.add("");
			info.add(String.format("UP       %s : You got connection at  %s", ++increment, getTime()));
		}
		else {
			down = getTime();
			info.add("");
			info.add(String.format("DOWN     %s : You lost connection at %s", ++increment, getTime()));
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
	
	private void notification() {
		SystemTray tray = SystemTray.getSystemTray();
		Image img = Toolkit.getDefaultToolkit().createImage("info.png");
		TrayIcon trayicon = new TrayIcon(img, "Lost Connection");
		trayicon.setImageAutoSize(true);
		try {
			tray.add(trayicon);
			trayicon.displayMessage("Lost Connection", "Connection was lost at " + getTime(), MessageType.INFO);
		} catch(AWTException e) {
			e.printStackTrace();
		}
	}
	
	private String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime now = LocalTime.now();
		return dtf.format(now);
	}
	
	public void addToNetstat(String str) {
		netstatList.add(str);
	}
	
	public void setRouter(boolean router) {
		this.router = router;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public void setPingNumber(String number) {
		this.number = number;
	}
	
	public void setPingSize(String packet) {
		this.packet = packet;
	}
	
	public void setTime(int time) {
		duration = Duration.seconds(time);
	}
	
	public void setRouterIP(String rout) {
		this.rout = rout;
	}
	
	public void setRoot(RootController root) {
		this.root = root;
	}
	
	public List<String> getFile() {
		return info;
	}*/
}
