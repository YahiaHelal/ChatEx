package com.yahia.chatio.ui.controllers;
import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.ui.managers.SceneNavigator;
import com.yahia.chatio.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActiveServersSceneController implements ActiveServersSceneListener {
    private final ObservableList<ServerConnection> serversList = FXCollections.observableArrayList();
    private final SceneNavigator navigator;


    public ActiveServersSceneController(SceneNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public ObservableList<ServerConnection> getServersList() {
        return this.serversList;
    }

    @Override
    public void addServer(ServerConnection connectionContext) {
        if(!serversList.contains(connectionContext)) {
            serversList.add(connectionContext);
        }
    }

    @Override
    public void onReturnButtonClicked() {
        navigator.showLoginScene();
    }
}
