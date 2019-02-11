package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Options {
	private StringProperty number;
	private StringProperty size;
	private StringProperty ping;
	private StringProperty warning;
	
	public Options() {
		this(null,null,null,null);
	}
	
	public Options(String number, String size, String ping, String warning) {
		this.number = new SimpleStringProperty(number);
		this.size = new SimpleStringProperty(size);
		this.ping = new SimpleStringProperty(ping);
		this.warning = new SimpleStringProperty(warning);
	}
	
	public void setSize(String size) {
		this.size.set(size);
	}
	
	public String getSize() {
		return size.get();
	}
	
	public StringProperty sizeProperty() {
		return size;
	}
	
	public void setPing(String ping) {
		this.ping.set(ping);
	}
	
	public String getPing() {
		return ping.get();
	}
	
	public StringProperty pingProperty() {
		return ping;
	}
	
	public void setNumber(String number) {
		this.number.set(number);
	}
	
	public String getNumber() {
		return number.get();
	}
	
	public StringProperty numberProperty() {
		return number;
	}
	
	public void setWarning(String warning) {
		this.warning.set(warning);
	}
	
	public String getWarning() {
		return warning.get();
	}
	
	public StringProperty warningProperty() {
		return warning;
	}
}
