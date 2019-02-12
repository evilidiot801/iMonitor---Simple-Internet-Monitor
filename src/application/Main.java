//APACHE VALIDATOR FOR IPS//
//
//FAIL IF LOSS IS OVER 50//
//OPTION TO SET THRESHOLD?//
//
//NETSTAT -a -b FOR PROGRAM IPS CONNECTING TO YOU//
//-e FOR ETHER NET OPTION//
//
//ADD CONFIG FILE TO ALOWE DEFAULT CONFIGURATION//
//USE MANIFEST TO RUN IN ADMIN MODE//

package application;

import java.io.IOException;

import application.model.PingOptions;
import application.model.Printer;
import application.controllers.ExitController;
import application.controllers.InfoController;
import application.controllers.PrintController;
import application.controllers.RootController;
import application.controllers.Net.NetTabController;
import application.controllers.Ping.PingOptionsController;
import application.controllers.Ping.PingTabController;
import application.controllers.Ping.RouterController;
import application.controllers.Ping.WebsiteController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	private Stage primaryStage;
	private GridPane rootLayout;
	private RootController rController;
	
	private PingOptions pingOptions;
	private PingTabController pingTabController;
	private NetTabController netTabController;
	private SplitPane infoOverview;
	private Print print;
	private Printer printer;
	private String number = "1";
	private String size = "32";
	private String ping = "3";
	private String warning = "Warning: Large numbers will slow service.";
	private String web = "www.google.com";
	private String rout = "192.168.1.1";
	private boolean isWeb = true;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("iMonitor");
		this.primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				showExit();
			}
		});
		createOptions();
		initRootLayout();
	}
	
	private void createOptions() {
		pingOptions = new PingOptions(number,size,ping,warning,web,rout,isWeb);
	}
	
	public void initRootLayout() {
		try {
			print = new Print();
			printer = new Printer();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (GridPane) loader.load();
			
			rController = loader.getController();
			rController.setMain(this);
			
			showInfoOverview(rController);
			
			primaryStage.setScene(new Scene(rootLayout));
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(500);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showInfoOverview(RootController root) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/InformationLayout.fxml"));
			infoOverview = (SplitPane) loader.load();
			InfoController infoController = loader.getController();
			//infoController.setMain(this);
			TabPane ping = showPing();
			TabPane net = showNet(root);
			//Dragable support = new Dragable();
			//support.addDragable(ping);
			//support.addDragable(net);
			infoOverview.getItems().addAll(ping,net);
			
			rootLayout.getChildren().add(infoOverview);
			GridPane.setConstraints(infoOverview, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TabPane showPing() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PingLayout.fxml"));
			TabPane pingOverview = (TabPane) loader.load();
			//pingOverview.getTabs().get(0).setGraphic(new Rectangle(16,16));
			pingTabController = loader.getController();
			pingTabController.addlisteners(pingOptions);
			
			return pingOverview;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TabPane showNet(RootController root) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/NetLayout.fxml"));
			TabPane netOverview = (TabPane) loader.load();
			netTabController = loader.getController();
			netTabController.setTasks(root);
			//netOverview.getTabs().get(0).setGraphic(new Rectangle(16,16));
			//pingTabController = loader.getController();
			
			return netOverview;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public NetTabController getNetController() {
		return netTabController;
	}
	
	public PingTabController getPingController() {
		return pingTabController;
	}
	
	public void showOptions() {
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
			
			PingOptionsController controller = loader.getController();
			controller.setOptions(pingOptions,this);
			controller.setStage(optionsStage);
			optionsScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER)
						controller.enterOptions();
				}
			});
			
			optionsStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PingOptions getPingOptions(){
		return pingOptions;
	}
	
	public void showWebsite() {
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
			controller.setStage(websiteStage);
			controller.setOptions(pingOptions,this);
			websiteScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER)
						controller.enterWebsite();
				}
			});
			
			websiteStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showRouter() {
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
			controller.setOptions(pingOptions,this);
			controller.setStage(routerStage);
			routerScene.setOnKeyPressed((event) -> {
				if(event.getCode() == KeyCode.ENTER)
					controller.enterRouter();
			});
			
			routerStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setRouter(String rout) {
		this.rout = rout;
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
			controller.setStage(printStage);
			
			printStage.showAndWait();
			
			return controller.getOk();
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public void showExit() {
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
			controller.setStage(rController, exitStage, printer);
			
			exitStage.showAndWait();
			
			exitStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> {
				exitStage.close();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//?? REWORK ??//
	public Printer getPrinterObject() {
		return printer;
	}
	
	public Print getPrinter() {
		return print;
	}
	////
	
	public static void main(String[] args) {
		launch(args);
	}
}
