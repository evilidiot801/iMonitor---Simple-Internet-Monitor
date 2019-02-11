package application;

//import java.util.concurrent.atomic.AtomicLong;

import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Dragable {
	private Tab currentTab;
	//DELET ME
	private TabPane currentPane;
	
	//private static final AtomicLong idGen = new AtomicLong();
	
	private final String draggingID = "Ima Draggin";
	
	public void addDragable(TabPane tabPane) {
		currentPane = tabPane;
		tabPane.getTabs().forEach(this::addDragHandlers);
		tabPane.getTabs().addListener((Change<? extends Tab> c) -> {
			while(c.next()) {
				if(c.wasAdded())
					c.getAddedSubList().forEach(this::addDragHandlers);
				if(c.wasRemoved())
					c.getRemoved().forEach(this::removeDragHandlers);
			}
		});
		//RECIEVE DROP
		tabPane.setOnDragOver(e -> {
			if(draggingID.equals(e.getDragboard().getString()) && currentTab != null && currentTab.getTabPane() != tabPane) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
		});
		tabPane.setOnDragDropped(e -> {
			if(draggingID.equals(e.getDragboard().getString()) && currentTab != null && currentTab.getTabPane() != tabPane) {
				currentTab.getTabPane().getTabs().remove(currentTab);
				tabPane.getTabs().add(currentTab);
				currentTab.getTabPane().getSelectionModel().select(currentTab);
			}
		});
	}
	
	/**
	 * @param tab
	 */
	private void addDragHandlers(Tab tab) {
		if(tab.getText() != null && ! tab.getText().isEmpty()) {
			Label label = new Label(tab.getText(), tab.getGraphic());
			tab.setText(null);
			tab.setGraphic(label);
		}
		
		Node graphic = tab.getGraphic();
		graphic.setOnMouseReleased(e -> {
			System.out.println(graphic.getId());
			System.out.println("release");
			Point2D mouseLocation = new Point2D(e.getScreenX(), e.getSceneY());
			Window window = currentPane.getScene().getWindow();
			Rectangle2D rect = new Rectangle2D(window.getX(),window.getY(),window.getHeight(),window.getWidth());
			if(! rect.contains(mouseLocation)) {
				TabPane pane = new TabPane();
				Stage stage = new Stage();
				stage.setTitle(currentTab.getText());
				stage.initModality(Modality.WINDOW_MODAL);
				pane.getTabs().add(currentTab);
				stage.setScene(new Scene(pane));
				stage.show();
			}
		});
		graphic.setOnDragDetected(e -> {
			Dragboard dragboard = graphic.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putString(draggingID);
			dragboard.setContent(content);
			//Change to change view of dragging tab
			dragboard.setDragView(graphic.snapshot(null,null));
			currentTab = tab;
		});
		//RECIEVE DROP?????
		graphic.setOnDragOver(e -> {														//Change for placement thresholds
            if (draggingID.equals(e.getDragboard().getString()) && currentTab != null && currentTab.getGraphic() != graphic) {
        		//e.acceptTransferModes(TransferMode.MOVE);
            }
		});
		//REQUEST TARGET?????
		//MIGHT NOT BE NEEDED//
		
		/*graphic.setOnDragDropped(e -> {
			if(draggingID.equals(e.getDragboard().getString()) && currentTab != null && currentTab.getGraphic() != graphic) {
				int index = tab.getTabPane().getTabs().indexOf(tab);
				currentTab.getTabPane().getTabs().remove(currentTab);
				tab.getTabPane().getTabs().add(index, currentTab);
				currentTab.getTabPane().getSelectionModel().select(currentTab);
            }
		});*/
		graphic.setOnDragDone(e -> currentTab = null);
	}
	
	private void removeDragHandlers(Tab tab) {
		tab.getGraphic().setOnDragDetected(null);
		tab.getGraphic().setOnDragOver(null);
		tab.getGraphic().setOnDragDropped(null);
		tab.getGraphic().setOnDragDone(null);
	}
}