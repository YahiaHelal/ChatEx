package com.yahia.chatio.ui.scenes;

import com.yahia.chatio.ui.controls.PromptTextField;
import com.yahia.chatio.ui.scenes.listeners.ServerLifeCycleListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class ServerLifeCycleScene {

    private BorderPane root;
    private PromptTextField serverNameField;
    private Button launchButton;
    private Button terminateButton;
    private Button returnButton;
    private Scene scene;

    private final ImageView returnIcon = new ImageView(
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/return.png")))
    );

    private ServerLifeCycleListener serverLifeCycleListener;

    public ServerLifeCycleScene() {
        initControls();
        buildUi();
        applyConstraints();
    }

    public Scene getScene() {
        return scene;
    }

    public void wireController(ServerLifeCycleListener listener) {
        this.serverLifeCycleListener = listener;
        setupActions();
    }

    private void initControls() {
        root = new BorderPane();


        serverNameField = new PromptTextField("Server Name");
        launchButton = new Button("Launch Server");
        terminateButton = new Button("Terminate Server");

        returnButton = new Button();
        returnButton.setGraphic(returnIcon);
        returnIcon.setFitWidth(16);
        returnIcon.setFitHeight(16);

        scene = new Scene(root, 600, 400);
    }

    private void buildUi() {

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(10));
        topBar.setLeft(returnButton);
        root.setTop(topBar);

        VBox centerBox = new VBox(15, serverNameField, launchButton, terminateButton);
        centerBox.setAlignment(Pos.CENTER);
        root.setCenter(centerBox);
    }

    private void applyConstraints() {
        double standardWidth = 260;
        double standardHeight = 35;


        serverNameField.setPrefWidth(standardWidth);
        serverNameField.setMaxWidth(standardWidth);

        launchButton.setPrefWidth(standardWidth);
        launchButton.setPrefHeight(standardHeight);

        terminateButton.setPrefWidth(standardWidth);
        terminateButton.setPrefHeight(standardHeight);
    }

    private void setupActions() {
        launchButton.setOnAction(event -> {
            if (serverLifeCycleListener != null) {
                serverLifeCycleListener.onLaunch(serverNameField.getText());
            }
        });

        terminateButton.setOnAction(event -> {
            if (serverLifeCycleListener != null) {
                serverLifeCycleListener.onTerminate(serverNameField.getText());
            }
        });

        returnButton.setOnAction(event -> {
            if (serverLifeCycleListener != null) {
                serverLifeCycleListener.onReturnButtonClicked();
            }
        });
    }
}
