package com.yahia.chatio.ui.scenes;


import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class ActiveServersScene {
    private final ListView<ServerConnection> serversListView;
    private final Scene serversScene;
    private final BorderPane serversBorderPane;
    private final Button returnButton;
    private final ImageView returnIcon;
    private ActiveServersSceneListener serversSceneListener;


    public ActiveServersScene() {
        serversBorderPane = new BorderPane();
        serversListView = new ListView<>();
        returnButton = new Button();
        serversScene = new Scene(serversBorderPane);
        returnIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/return.png"))));
        buildUi();
    }

    public Scene getScene(){
        return serversScene;
    }

    public void wireController(ActiveServersSceneListener listener) {
        this.serversSceneListener = listener;
        serversListView.setItems(serversSceneListener.getServersList());
        setupActions();
    }

    public void setupActions() {
        returnButton.setOnAction(actionEvent -> serversSceneListener.onReturnButtonClicked());
    }

    private void buildUi() {
        serversBorderPane.setCenter(serversListView);
        serversBorderPane.setTop(returnButton);
        returnButton.setGraphic(returnIcon);
        returnIcon.setFitWidth(16);
        returnIcon.setFitHeight(16);
    }
}
