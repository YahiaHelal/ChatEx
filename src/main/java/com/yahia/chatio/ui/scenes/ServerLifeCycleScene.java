package com.yahia.chatio.ui.scenes;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.ui.scenes.listeners.ServerLifeCycleListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ServerLifeCycleScene {

    private GridPane root;
    private TextField portTextField;
    private TextField serverNameTextField;
    private Button launchButton;
    private Button terminateButton;
    private Scene scene;

    private ServerLifeCycleListener serverLifeCycleListener;

    //TODO: add server capacity as a field entered by the user
    public ServerLifeCycleScene() {
        initControls();
        applyConstraints();
        buildUi();
    }

    public Scene getScene() {
        return scene;
    }

    public void wireController(ServerLifeCycleListener listener) {
        this.serverLifeCycleListener = listener;
        setupActions();
    }


    private void initControls() {
        root = new GridPane();

        serverNameTextField = new TextField();
        serverNameTextField.setPromptText("Server Name");

        launchButton = new Button("Launch Server");
        terminateButton = new Button("Terminate Server");
        scene = new Scene(root, 600, 400);

    }

    private void buildUi() {
        root.add(serverNameTextField, 0, 0);
        root.add(launchButton, 0, 1);
        root.add(terminateButton, 0, 2);
    }

    private void applyConstraints() {
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(15);
        root.setPadding(new Insets(20));

        serverNameTextField.setPrefWidth(260);

        launchButton.setPrefWidth(260);
        launchButton.setPrefHeight(35);

        terminateButton.setPrefWidth(260);
        terminateButton.setPrefHeight(35);
    }


    private void setupActions() {
        launchButton.setOnAction(event -> {
            if (serverLifeCycleListener != null) {
                serverLifeCycleListener.onLaunch(serverNameTextField.getText());
            }
        });

        terminateButton.setOnAction(actionEvent -> {
            if(serverLifeCycleListener != null) {
                serverLifeCycleListener.onTerminate(serverNameTextField.getText());
            }
        });
    }
}
