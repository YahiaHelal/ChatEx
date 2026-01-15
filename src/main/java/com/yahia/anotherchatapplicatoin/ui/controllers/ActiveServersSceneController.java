package com.yahia.anotherchatapplicatoin.ui.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.client.session.ServerConnection;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ActiveServersSceneController implements ActiveServersSceneListener {
    private final SceneNavigator navigator;
    private final Client client;
    private final ListView<ServerConnection> activeServers;
    public ActiveServersSceneController(SceneNavigator navigator, Client client, ListView<ServerConnection> list) {
        this.navigator = navigator;
        this.client = client;
        this.activeServers = list;
    }

    @Override
    public void update() {
        activeServers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ServerConnection server, boolean empty) {
                super.updateItem(server, empty);

                if(empty || server == null) {
                    setGraphic(null);
                    return;
                }

                ImageView icon = new ImageView(server.icon());
                Label name = new Label(String.format("%s:%s", server.ip(), server.port()));
                Button connect = new Button("Connect");

                connect.setOnAction(actionEvent -> {
                    navigator.showChatScene(client);
                });
                setGraphic(new HBox(10, icon, name, connect));
            }
        });
    }

    @Override
    public void onReturnButtonClicked() {
        navigator.showLoginScene();
    }
}
