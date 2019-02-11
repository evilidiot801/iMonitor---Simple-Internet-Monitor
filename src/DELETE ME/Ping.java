package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {
	/*private String out;
	private String ping;
	private boolean connection;
	
	public boolean getConnection() {
		return connection;
	}
	
	public void setConnection(boolean connection) {
		this.connection = connection;
	}
	
	public String start(boolean c, boolean web, String w, String rout, String n, String p) {
		if(web)
			ping = w;
		else
			ping = rout;
		out = "";
		return test(c,n,p);
	}
	
	private String test(boolean c, String n, String p) {
		try {
			String command = "ping -n " + n + " -l " + p + " " + ping;
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
			System.out.print(scanner.hasNextLine());
			while(scanner.hasNextLine()) {
				String result = scanner.nextLine();
				
				if(result.contains("Reply")) {
					if(c == false) {
						System.out.println('\r' + "Connected");
						out = "Connected!";
						connection = true;
					}
				}
				else {
					if(c) {
						System.out.println('\r' + "Lost Connection!");
						out = "Lost Connection!";
						connection = false;
					}
				}
				
				if(result.contains("Average")) {
					ms = result.substring(result.indexOf("Average"));
					Matcher matcher = Pattern.compile("\\d+").matcher(ms);
					matcher.find();
					ms = matcher.group();
					matcher.reset();
					matcher = Pattern.compile("\\d+\\%").matcher(result);
					matcher.find();
					loss = matcher.group();
				}
			}
			scanner.close();
			out += "." + ms + "." + loss;
			System.out.println(out);
		} catch (IOException e) {
			e.printStackTrace();
			return "Error! IOException";
		}
		return out;
	}*/
}
