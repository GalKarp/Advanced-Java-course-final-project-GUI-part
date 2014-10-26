package view;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import war.controller.WarUIEventsListener;

public class Launchertab extends AnchorPane   {
    private ConsoleApp theApplication;
    private List<WarUIEventsListener> allListener;
    private Button connectLauncher;
    private Button addLauncher;
    private TextField launcherId;
    public Launchertab(ConsoleApp theApplication ,List<WarUIEventsListener> allListeners) {
	this.theApplication = theApplication;
	this.allListener = allListeners;

	connectLauncher = new Button("Connect to server");
	launcherId = new TextField();
	addLauncher = new Button("Resquest & Add Launcher from server");
	setMaxSize(285.0, 371.0);

	launcherId.setPromptText("LauncherID");
	launcherId.setMaxSize(221, 25);
	launcherId.setLayoutX(14.0);
	launcherId.setLayoutY(37.0);

	addLauncher.setLayoutX(14.0);
	addLauncher.setLayoutY(78.0);

	connectLauncher.setLayoutX(14.0);
	connectLauncher.setLayoutY(5.0);

	getChildren().addAll(connectLauncher,launcherId, addLauncher);
	addLauncher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String id = launcherId.getText();
				for (WarUIEventsListener l : allListener) {
					// sending missile details to the relevant GUI launcher
					l.addLauncherThroughClient(id);
					System.out.println(id);

				}
			}
		}).start();
            }
        });
	connectLauncher.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
		new Thread(new Runnable() {
		    @Override
		    public void run() {
			for (WarUIEventsListener l : allListener) {
			    // sending missile details to the relevant GUI launcher
			    l.connectToServer();
			}
		    }
		}).start();
            }
        });

    }
}
