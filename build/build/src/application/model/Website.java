package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Website {
	private StringProperty website;
	
	public Website() {
		this(null,null);
	}
	
	public Website(String website, String warning) {
		this.website = new SimpleStringProperty(website);
	}
	
	public void setWebsite(String website) {
		this.website.set(website);
	}
	
	public String getWebsite() {
		return website.get();
	}
	
	public StringProperty websiteProperty() {
		return website;
	}
}
