package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.model.NetStatList;

public class NetStat {
	private NetStatList list;
	
	public void start(NetStatList list) {
		this.list = list;
		test(list);
	}
	
	private void test(NetStatList list) {
		try {
			String cmd[] = {"CMD","/C","netstat -a -b" };
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			
			InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);
			String result;
			while((result = reader.readLine()) != null) {
				System.out.println(result+"\n");
				//list.addToList(result);
			}
			try {
				process.waitFor();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
