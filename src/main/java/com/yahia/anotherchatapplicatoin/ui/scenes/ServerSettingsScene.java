package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ServerSettingsListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ServerSettingsScene {

    private GridPane root;
    private TextField ipTextField;
    private TextField portTextField;
    private TextField serverNameTextField;
    private Button launchButton;
    private Button terminateButton;
    private Scene scene;

    private ServerSettingsListener serverSettingsListener;

    public ServerSettingsScene() {
        initControls();
        applyConstraints();
        buildUi();
    }

    public Scene getScene() {
        return scene;
    }

    public void wireController(ServerSettingsListener listener) {
        this.serverSettingsListener = listener;
        setupActions();
    }


    private void initControls() {
        root = new GridPane();

        ipTextField = new TextField();
        ipTextField.setPromptText("Server IP (e.g. 127.0.0.1)");

        portTextField = new TextField();
        portTextField.setPromptText("Server Port (e.g. 8080)");
        serverNameTextField = new TextField();
        serverNameTextField.setPromptText("Server Name");

        launchButton = new Button("Launch Server");
        terminateButton = new Button("Terminate Server");
        scene = new Scene(root, 600, 400);

    }

    private void buildUi() {
        root.add(ipTextField, 0, 0);
        root.add(portTextField, 0, 1);
        root.add(serverNameTextField, 0, 2);
        root.add(launchButton, 0, 3);
        root.add(terminateButton, 0, 4);
    }

    private void applyConstraints() {
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(15);
        root.setPadding(new Insets(20));

        ipTextField.setPrefWidth(260);
        portTextField.setPrefWidth(260);
        serverNameTextField.setPrefWidth(260);

        launchButton.setPrefWidth(260);
        launchButton.setPrefHeight(35);

        terminateButton.setPrefWidth(260);
        terminateButton.setPrefHeight(35);
    }

    private void setupActions() {
        launchButton.setOnAction(event -> {
            if (serverSettingsListener != null) {
                serverSettingsListener.onLaunch(serverNameTextField.getText(), portTextField.getText());
            }
        });

        terminateButton.setOnAction(actionEvent -> {
            if(serverSettingsListener != null) {
                serverSettingsListener.onTerminate(serverNameTextField.getText());
            }
        });
    }
}
