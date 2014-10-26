package view;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import war.controller.WarUIEventsListener;

public class ConsoleApp extends Application     {
    private Stage primaryStage;
    private Missiletab missiletab;
    private Launchertab launcherTab;
    private List<WarUIEventsListener> allListeners;
    private String launcherId;
    

    public ConsoleApp() {
	this.allListeners = LaunchersPanel.getAllListeners();
	this.launcherId = LaunchersPanel.getLauncherID();
	
    }

    public void start(Stage primaryStage) throws Exception {
	
        final Tab tabMissile = new Tab();
        tabMissile.setText("Missile");
        final Tab tabLauncher = new Tab();
        tabLauncher.setText("Launcher");
	TabPane tabPane = new TabPane();
	tabPane.setId("mainPane");
	
	missiletab = new Missiletab(this , launcherId , allListeners);
	launcherTab = new Launchertab(this , allListeners);

	tabMissile.setContent(missiletab);
	tabLauncher.setContent(launcherTab);
	tabPane.getTabs().addAll(tabMissile , tabLauncher);
	
	Scene scene = new Scene(tabPane, 255, 240);
	scene.getStylesheets().add(this.getClass().getResource("ClientStyle").toExternalForm());
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    	public static void main(String[] args) {
    		launch(args);
    	}

	public Stage getPrimaryStage() {
	    return primaryStage;
	}


    	
}
