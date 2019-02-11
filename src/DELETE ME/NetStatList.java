package application.model;

import java.util.ArrayList;
import java.util.List;

public class NetStatList {
	private List<String> info = new ArrayList<String>();
	
	public void addToList(String str) {
		info.add(str);
	}
	
	public List<String> getList(){
		return info;
	}
	
	public void clearList() {
		info.clear();
	}
}
