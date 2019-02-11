package application.model;

import java.util.ArrayList;
import java.util.List;

import application.controllers.InfoController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PingOptions {
	private StringProperty number;
	private StringProperty size;
	private StringProperty ping;
	private StringProperty website;
	private StringProperty router;
	private StringProperty warning;
	private BooleanProperty web;
	private BooleanProperty connection;
	
	public PingOptions(String number, String size, String ping, String warning, String website, String router, boolean web) {
		this.number = new SimpleStringProperty(number);
		this.size = new SimpleStringProperty(size);
		this.ping = new SimpleStringProperty(ping);
		this.warning = new SimpleStringProperty(warning);
		this.website = new SimpleStringProperty(website);
		this.router = new SimpleStringProperty(router);
		this.web = new SimpleBooleanProperty(web);
		this.connection = new SimpleBooleanProperty(false);
	}
	
	public void initalize() {
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
	
	public void setWebsite(String website) {
		this.website.set(website);
	}
	
	public String getWebsite() {
		return website.get();
	}
	
	public StringProperty websiteProperty() {
		return website;
	}
	
	public void setRouter(String router) {
		this.router.set(router);
	}
	
	public String getRouter() {
		return router.get();
	}
	
	public StringProperty routerProperty() {
		return router;
	}
	
	public void setWeb(boolean web) {
		this.web.set(web);
	}
	
	public boolean isWeb() {
		return web.get();
	}
	
	public BooleanProperty webProperty() {
		return web;
	}
	
	public void setConnection(boolean connection) {
		this.connection.set(connection);
	}
	
	public boolean getConnection() {
		return connection.get();
	}
	
	public BooleanProperty connectionProperty() {
		return connection;
	}
}
