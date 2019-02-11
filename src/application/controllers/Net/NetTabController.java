package application.controllers.Net;

import java.util.ArrayList;
import java.util.List;

import application.NetTasks;
import application.controllers.RootController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NetTabController {
	@FXML
	private VBox infoBox;
	@FXML
	private ProgressIndicator progress;
	@FXML
	private ToggleGroup startStop;
	@FXML
	private ToggleButton start;
	@FXML
	private ToggleButton stop;
	@FXML
	private Label average;
	@FXML
	private Label highest;
	@FXML
	private Label isWeb;
	@FXML
	private Label conn;
	@FXML
	private Button clear;
	@FXML
	private Button refresh;
	
	private NetTasks task;
	private RootController rController;
	private BooleanProperty alive = new SimpleBooleanProperty(true);
	//private ObservableList<String> list = FXCollections.observableArrayList();
	
	public void setTasks(RootController controller) {
		rController = controller;
		task = new NetTasks(this,controller);
		
		stop.setDisable(true);
		refresh.setDisable(alive.get());
		alive.addListener((obs,ov,nv) -> {
			refresh.setDisable(nv);
			progress.setVisible(nv);
		});
		//setServices(task);
		/*list.addListener((ListChangeListener.Change<? extends String> c) -> {
			if(c.wasAdded()) {
				for(String s : c.getAddedSubList()) {
					System.out.println(s);
				}
			}
		});*/
	}
	
	/*private void setServices(NetUpdate task) {
		task.setPeriod(new Duration(1000));
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
			}
		});
		task.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent e) {
				System.out.println("Failed");
			}
		});
	}*/
	
	@FXML
	public void handleStart() throws Exception {
		progress.setVisible(true);
		start.setDisable(true);
		//stop.setDisable(false);
		task.setOn(true);
		
		Text text = new Text("Started");
		text.setStyle("-fx-font-weight: 700;");
		HBox started = new HBox(text);
		started.setAlignment(Pos.CENTER);
		addToInfo(started,"greenStyle");
		
		task.start();
	}
	
	@FXML
	public void handleStop() throws Exception {
		progress.setVisible(false);
		start.setDisable(false);
		stop.setDisable(true);
		task.setOn(false);
		
		Text text = new Text("Stopped");
		text.setStyle("-fx-font-weight: 700;");
		HBox stopped = new HBox(text);
		stopped.setAlignment(Pos.CENTER);
		addToInfo(stopped,"redStyle");
	}
	
	@FXML
	public void handleClear() {
		infoBox.getChildren().clear();
	}
	
	@FXML
	public void handleRefresh() {
		progress.setVisible(true);
		alive.set(true);
		task.start();
	}
	
	public void createInfo(String[] info) {
		Platform.runLater(new Runnable() {
			public void run() {
				if(rController.getAdmin().equals("-a")) {
					HBox box = new HBox();
					for(String s : info) {
						Text text = new Text(s);
						if(s.equals("Connection: ") || s.equals("Status: ")) {
							text.setStyle("-fx-font-weight: 700");
						}
						box.setAlignment(Pos.CENTER);
						box.getChildren().add(text);
					}
					addToInfo(box,"grayStyle");
				}
				if(rController.getAdmin().equals("-b")) {
					HBox box = new HBox();
					if(info[0].contains("*")) {
						Text txt = new Text(info[0]);
						box.getChildren().add(txt);
						box.setAlignment(Pos.CENTER);
						addToInfo(box,"redStyle");
					}
					else if(info[0].contains("[")) {
						Text txt = new Text(info[0]);
						box.getChildren().add(txt);
						box.setAlignment(Pos.CENTER);
						addToInfo(box,"grayStyle");
					}
					else {
						String txt = "";
						for(String s : info) {
							txt += s;
						}
						Text text = new Text(txt);
						text.setStyle( "font-size: 10px;" );
						box.setAlignment(Pos.CENTER);
						box.getChildren().add(text);
					}
				}
			}
		});
	}
	
	public void addToInfo(HBox txt, String style) {
		Platform.runLater(new Runnable() {
			public void run() {
				if(txt != null && task.getOn()) {
					txt.setSpacing(16);
					BorderPane textArea = new BorderPane(txt);
					textArea.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
					textArea.getStyleClass().addAll(style,"infoStyle");
					BorderPane.setAlignment(txt, Pos.CENTER);
					infoBox.getChildren().add(0,textArea);
				}
			}
		});
	}
	
	public List<String[]> getList(){
		return task.getList();
	}
	
	public NetTasks getTask() {
		return task;
	}
	
	public void setAlive(boolean alive) {
		this.alive.set(alive);
	}
}
