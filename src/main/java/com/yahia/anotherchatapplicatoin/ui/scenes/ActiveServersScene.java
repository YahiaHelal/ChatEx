package com.yahia.anotherchatapplicatoin.ui.scenes;


import com.yahia.anotherchatapplicatoin.client.session.ServerConnection;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.scene.layout.HBox;


public class ActiveServersScene {
    private final Scene activeServersScene;
    private final Button returnButton;
    private  ActiveServersSceneListener serversSceneListener;

    public ActiveServersScene() {
        ListView<ServerConnection> activeServers = new ListView<>();
        returnButton = new Button();
        activeServersScene = new Scene(new HBox(10, returnButton, activeServers));
    }

    public Scene getScene() {
        return activeServersScene;
    }
    public void wireController(ActiveServersSceneListener listener) {
        this.serversSceneListener = listener;
        setupActions();
    }

    private void setupActions() {
        returnButton.setOnAction(actionEvent -> {
            serversSceneListener.onReturnButtonClicked();
        });
    }
}
