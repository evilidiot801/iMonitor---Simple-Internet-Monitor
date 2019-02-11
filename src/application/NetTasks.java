package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.controllers.RootController;
import application.controllers.Net.NetTabController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class NetTasks{
	private ObservableList<String[]> list = FXCollections.observableArrayList();
	private List<String[]> logList = new ArrayList<String[]>();
	private NetTabController controller;
	private RootController rController;
	private ScheduledService<Boolean> service;
	private BooleanProperty on = new SimpleBooleanProperty(true);
	
	public NetTasks(NetTabController controller, RootController rController) {
		this.controller = controller;
		this.rController = rController;
		list.addListener((ListChangeListener.Change<? extends String[]> c) -> {
			if(c.next()) {
				if(c.wasAdded()) {
					for(String[] s : c.getAddedSubList()) {
						if(on.get()) {
							logList.add(s);
							controller.createInfo(s);
						}
						list.clear();
					}
				}
			}
		});
	}
		
	private Runnable getTask() {
		return new Runnable() {
			public void run() {
				try {
					String cmd[] = {"CMD","/C","netstat "+rController.getAdmin()};
					ProcessBuilder builder = new ProcessBuilder(cmd);
					Process process = builder.start();
					InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
					
					BufferedReader reader = new BufferedReader(streamReader);
					String result;
					Matcher matcher;
					if(rController.getAdmin().equals("-a")) {
						while((result = reader.readLine()) != null) {
							String content = result;
							matcher = Pattern.compile("\\d+.\\d?+.\\d?+\\d?+:\\d+").matcher(result);
							if(matcher.find() && !result.contains("peters-pc") && !result.contains("*:*")) {
								content = content.replaceFirst("\\S+\\s+\\S+\\s+","");   
								String stat = content.replaceFirst("\\S+", "");
								stat = stat.replaceAll("\\s+", "");
								String conn = content.replaceFirst("\\s+", "");
								conn = conn.replaceFirst("\\s+\\S+", "");
								String[] string = new String[] { "Connection: ",conn,"Status: ",stat };
								list.add(string);
							}
						}
					}
					if(rController.getAdmin().equals("-b")) {
						while((result = reader.readLine()) != null) {
							//String content = result;
							//Matcher adminmatcher = Pattern.compile("\\?\\s+\\S+\\s+\\S+\\s+").matcher(result);
							Matcher nameMatcher = Pattern.compile("[\\S+]").matcher(result);
							String[] string = new String[1];
							/*if(adminmatcher.find()) {
								content = content.replaceFirst("\\?\\s+\\S+\\s+\\S+\\s+", "");
								String stat = content.replaceFirst("\\S+\\s+", "");
								String conn = content.substring(0);
								String[] string = new String[] { "Connection: ",conn,"Status: ",stat };
								list.add(string);
							}*/
							if(nameMatcher.find()) {
								string[0] = result;
							}
							if(result.contains("Can not obtain ownership information")) {
								string[0] = "*"+result+"*";
							}
							list.add(string);
						}
					}
					System.out.println("Waiting...");
					//process.waitFor();
					if(process.isAlive() == false) {
						System.out.println("Closing...");
						streamReader.close();
						reader.close();
						
						//for refresh button -ON-
						controller.setAlive(false);
						//for constantly running -OFF-
						if(on.get()) {
							//System.out.println("Restarting...");
							//start();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public void setOn(boolean value) {
		on.set(value);
	}
	
	public boolean getOn() {
		return on.get();
	}
	
	public void start() {
		Thread thread = new Thread(getTask());
		thread.start();
	}
	
	public void interrupt() {
		Thread.interrupted();
	}
	
	public List<String[]> getList(){
		if(logList.size() > 0) {
			return logList;
		}
		return null;
	}
}