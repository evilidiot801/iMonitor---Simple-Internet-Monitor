//ADD ENTER BUTTON CONTROL//

package application;

import java.io.IOException;

import application.model.Options;
import application.model.Printer;
import application.model.Router;
import application.model.Website;
import application.controllers.ExitController;
import application.controllers.InfoController;
import application.controllers.OptionsController;
import application.controllers.PrintController;
import application.controllers.RootController;
import application.controllers.RouterController;
import application.controllers.WebsiteController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	private Stage primaryStage;
	private VBox rootLayout;
	
	private Options options;
	private Website website;
	private Router router;
	private Input input;
	private Print print;
	private Printer printer;
	private InfoController infoController;
	private String number = "1";
	private String size = "32";
	private String ping = "1";
	private String warning = "Warning: Large numbers will slow service.";
	private String web = "www.google.com";
	private String rout = "192.168.1.1";
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("iMonitor");
		
		initRootLayout();
		
		showMonitorOverview();
	}
	
	public void initRootLayout() {
		try {
			options = new Options(number,size,ping,warning);
			website = new Website(web," ");
			input = new Input(number,size,ping,web,rout);
			router = new Router(rout);
			print = new Print();
			printer = new Printer();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (VBox) loader.load();
			
			RootController controller = loader.getController();
			controller.setMain(this);
			input.setRoot(controller);
			
			primaryStage.setScene(new Scene(rootLayout));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMonitorOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/InformationLayout.fxml"));
			StackPane infoOverview = (StackPane) loader.load();
			
			infoController = loader.getController();
			input.initialize();
			
			rootLayout.getChildren().add(infoOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InfoController getInfoController() {
		return infoController;
	}
	
	public boolean showOptions() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/OptionsLayout.fxml"));
			GridPane optionsOverview = (GridPane) loader.load();
			
			Stage optionsStage = new Stage();
			optionsStage.setTitle("iMonitor - Options");
			optionsStage.initModality(Modality.WINDOW_MODAL);
			optionsStage.initOwner(primaryStage);
			optionsStage.setResizable(false);
			Scene optionsScene = new Scene(optionsOverview);
			optionsStage.setScene(optionsScene);
			
			OptionsController controller = loader.getController();
			controller.setOptions(options);
			controller.setStage(optionsStage);
			optionsScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER)
						controller.enter();
				}
			});
			
			optionsStage.showAndWait();
			
			return controller.ok();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Options getOptions(){
		return options;
	}
	
	public String showWebsite() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/application/view/WebsiteLayout.fxml"));
			GridPane websiteView = (GridPane) loader.load();
			
			Stage websiteStage = new Stage();
			websiteStage.setTitle("iMonitor - Website");
			websiteStage.initModality(Modality.WINDOW_MODAL);
			websiteStage.initOwner(primaryStage);
			websiteStage.setResizable(false);
			Scene websiteScene = new Scene(websiteView);
			websiteStage.setScene(websiteScene);
			
			WebsiteController controller = loader.getController();
			controller.setWebsite(website,websiteStage);
			websiteScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER)
						controller.enter();
				}
			});
			
			websiteStage.showAndWait();
			
			return controller.getWebsite();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public Website getWebsite() {
		return website;
	}
	
	public String showRouter() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RouterLayout.fxml"));
			GridPane routerView = (GridPane) loader.load();
			
			Stage routerStage = new Stage();
			routerStage.setTitle("iMonitor - Router");
			routerStage.initModality(Modality.WINDOW_MODAL);
			routerStage.initOwner(primaryStage);
			routerStage.setResizable(false);
			Scene routerScene = new Scene(routerView);
			routerStage.setScene(routerScene);
			
			RouterController controller = loader.getController();
			controller.setRouter(rout,routerStage,router);
			routerScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER)
						controller.enter();
				}
			});
			
			routerStage.showAndWait();
			
			return controller.getRouter();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public Router getRouter() {
		return router;
	}
	
	public void showHelp() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/HelpLayout.fxml"));
			GridPane helpView = (GridPane) loader.load();
			
			Stage helpStage = new Stage();
			helpStage.setTitle("iMonitor - Help");
			helpStage.initModality(Modality.WINDOW_MODAL);
			helpStage.initOwner(primaryStage);
			helpStage.setResizable(false);
			helpStage.setScene(new Scene(helpView));
			
			helpStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean showPrint() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PrintLayout.fxml"));
			GridPane printView = (GridPane) loader.load();
			
			Stage printStage = new Stage();
			printStage.setTitle("iMonitor - Print");
			printStage.initModality(Modality.WINDOW_MODAL);
			printStage.initOwner(primaryStage);
			printStage.setResizable(false);
			printStage.setScene(new Scene(printView));
			
			PrintController controller = loader.getController();
			controller.setStage(printStage,printer);
			
			printStage.showAndWait();
			
			/*exitStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					exitStage.close();
				}
			});*/
			return controller.getOk();
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public Boolean showExit() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ExitLayout.fxml"));
			GridPane exitView = (GridPane) loader.load();
			
			Stage exitStage = new Stage();
			exitStage.setTitle("iMonitor - Exit");
			exitStage.initModality(Modality.WINDOW_MODAL);
			exitStage.initOwner(primaryStage);
			exitStage.setResizable(false);
			exitStage.setScene(new Scene(exitView));
			
			ExitController controller = loader.getController();
			controller.setStage(exitStage,printer);
			
			exitStage.showAndWait();
			
			exitStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					exitStage.close();
				}
			});
			return controller.getOk();
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public Printer getPrinterObject() {
		return printer;
	}
	
	public Print getPrinter() {
		return print;
	}
	
	public Input getInput() {
		return input;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
