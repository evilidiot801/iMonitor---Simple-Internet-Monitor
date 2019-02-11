package application;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {
	private String out;
	private String ping;
	private boolean connection;
	
	public boolean getConnection() {
		return connection;
	}
	
	public void setConnection(boolean connection) {
		this.connection = connection;
	}
	
	public String start(boolean c, boolean r, String w, String rout, String n, String p) {
		if(r)
			ping = rout;
		else
			ping = w;
		out = "";
		return test(c,n,p);
	}
	
	private String test(boolean c, String n, String p) {
		try {
			Process process = Runtime.getRuntime().exec("ping -n " + n + " -l " + p + " " + ping);
			try {
				process.waitFor();
			} catch (Exception e) { }
			process.destroy();
			Scanner scanner = new Scanner(process.getInputStream());
			String result = "";
			while(scanner.hasNext()) {
				String str = scanner.next();
				result += str;
			}
			String ms = result.substring(result.indexOf("Average"));
			Matcher matcher = Pattern.compile("\\d+").matcher(ms);
			matcher.find();
			ms = matcher.group();
			matcher.reset();
			matcher = Pattern.compile("\\d+\\%").matcher(result);
			matcher.find();
			String loss = matcher.group();

			boolean response = result.contains("Reply");
			scanner.close();
			out = "." + ms + "." + loss;
			if(response) {
				if(c == false) {
					System.out.println('\r' + "Connected");
					out = "Connected!" + "." + ms + "." + loss;
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
		} catch (IOException e) {
			e.printStackTrace();
			return "Error! IOException";
		}
		return out;
	}
}
