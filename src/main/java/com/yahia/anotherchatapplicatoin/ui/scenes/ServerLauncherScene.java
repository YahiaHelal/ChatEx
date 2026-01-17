package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ServerLauncherListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class ServerLauncherScene {

    private GridPane root;
    private TextField ipTextField;
    private TextField portTextField;
    private Button launchButton;
    private Scene scene;
//    private ImageView returnIcon;
//    private Button returnButton;

    private ServerLauncherListener serverLauncherListener;

    public ServerLauncherScene() {
        initControls();
        applyConstraints();
        setupActions();
        buildUi();

    }

    public Scene getScene() {
        return scene;
    }

    public void wireController(ServerLauncherListener listener) {
        this.serverLauncherListener = listener;
    }


    private void initControls() {
        root = new GridPane();

        ipTextField = new TextField();
        ipTextField.setPromptText("Server IP (e.g. 127.0.0.1)");

        portTextField = new TextField();
        portTextField.setPromptText("Server Port (e.g. 8080)");

        launchButton = new Button("Launch Server");

        scene = new Scene(root, 600, 400);

//        returnIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/return.png"))));
//        returnIcon.setFitHeight(16);
//        returnIcon.setFitWidth(16);
//
//        returnButton = new Button();
//        returnButton.setGraphic(returnIcon);
    }

    private void buildUi() {
        root.add(ipTextField, 0, 0);
        root.add(portTextField, 0, 1);
        root.add(launchButton, 0, 2);
    }

    private void applyConstraints() {
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(15);
        root.setPadding(new Insets(20));

        ipTextField.setPrefWidth(260);
        portTextField.setPrefWidth(260);
        launchButton.setPrefWidth(260);
        launchButton.setPrefHeight(35);
    }

    private void setupActions() {
        launchButton.setOnAction(event -> {
            if (serverLauncherListener != null) {
                serverLauncherListener.onLaunchButtonClicked(portTextField.getText());
            }
        });
    }
}
