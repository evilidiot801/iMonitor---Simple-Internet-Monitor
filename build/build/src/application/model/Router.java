package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Router {
	private StringProperty router;
	
	public Router() {
		this(null);
	}
	
	public Router(String router) {
		this.router = new SimpleStringProperty(router);
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
}
