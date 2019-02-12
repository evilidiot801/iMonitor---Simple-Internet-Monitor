//CLEAR INFO LIST//
//REMOVE REFERENCES TO VARIABLES OUTSIDE OF THREAD//



package application;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.model.PingOptions;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//import javafx.concurrent.Task;

public class Tasks extends ScheduledService<String> {
	private PingOptions pingoptions;
	
	public Tasks(PingOptions pingoptions) {
		this.pingoptions = pingoptions;
	}
	
	/*public Task createTask() {
		Task<String> task = new Task<String>() {
			try {
				System.out.println("running");
				output = startPing();
				boolean log = false;
				if(output.contains("Lost Connection"))
					log = true;
				if(output.contains("Connected")) {
					String conn = output.substring(0, output.indexOf("."));
					String ms = output.substring(output.indexOf("."));
					String loss = ms.substring(ms.indexOf("."));
					ms = ms.substring(0, ms.indexOf("."));
					
					if(log) {
						connection = conn;
						log = false;
					}
					info = "Ping Results: " + loss + " Loss : Average = " + ms + "ms.";
				}
				if(output.contains("Lost")) {
					notify = !notify;
					info = output;
				}
				Thread.sleep(Integer.parseInt(pingoptions.getPing())*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
	
	public Task<String> createTask() {
		return new Task<String>() {
			@Override
			public String call() throws IOException {
				String out = "";
				//CHANGED
				String command = "ping -n " + pingoptions.getNumber() + " -l " + pingoptions.getSize() + " ";
				if(pingoptions.isWeb())
					command += pingoptions.getWebsite();
				else
					command += pingoptions.getRouter();
				String cmd[] = { "CMD", "/C", command };
				ProcessBuilder builder = new ProcessBuilder(cmd);
				Process process = builder.start();
				try {
					process.waitFor();
				} catch (Exception e) { 
					e.printStackTrace(); 
				}
				Scanner scanner = new Scanner(process.getInputStream());
				scanner.useDelimiter("\n");
				String ms = null;
				String loss = null;
				while(scanner.hasNextLine()) {
					String result = scanner.nextLine();
					
					if(result.contains("Reply")) {
						if(pingoptions.getConnection() == false) {
							System.out.println('\r' + "Connected");
							out += "Connected!";
							pingoptions.setConnection(true);
						}
					}
					if(result.contains("could not find host")) {
						if(pingoptions.getConnection()) {
							System.out.println('\r' + "Lost Connection!");
							out += "Lost Connection!";
							pingoptions.setConnection(false);
						}
					}
					
					Matcher matcher;
					if(result.contains("Lost")) {
						matcher = Pattern.compile("\\d+\\%").matcher(result);
						matcher.find();
						loss = matcher.group();
						loss = loss.replaceAll("%", "");
						matcher.reset();
					}
					if(result.contains("Average")) {
						ms = result.substring(result.indexOf("Average"));
						matcher = Pattern.compile("\\d+").matcher(ms);
						matcher.find();
						ms = matcher.group();
					}
				}
				scanner.close();
				out += "." + ms + "." + loss;
				System.out.println(out);
				return out;
			}
		};
	}
	
	/*public String startNet(RunNetStat runnetstat) throws Exception {
		this.runnetstat = runnetstat;
		Future<String> runNet = netService.submit(runnetstat);
		return runNet.get();
	}*/
}
