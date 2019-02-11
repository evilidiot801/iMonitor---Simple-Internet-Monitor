package application;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.model.PingOptions;

public class RunPing implements Callable<String> {
	private PingOptions options;
	
	public void setRunPing(PingOptions options) {
		this.options = options;
	}
	
	@Override
	public String call() {
		String out = "";
		try {
			String command;
			if(options.isWeb())
				command = "ping -n " + options.getNumber() + " -l " + options.getSize() + " " + options.getWebsite();
			else
				command = "ping -n " + options.getNumber() + " -l " + options.getSize() + " " + options.getRouter();
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
					if(options.getConnection() == false) {
						System.out.println('\r' + "Connected");
						out = "Connected!";
						options.setConnection(true);
					}
				}
				if(!out.equals("Connected!")) {
					if(options.getConnection()) {
						System.out.println('\r' + "Lost Connection!");
						out = "Lost Connection!";
						options.setConnection(false);
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
		} catch (IOException e) {
			e.printStackTrace();
			return "Error! IOException";
		}
		return out;
	}
}
