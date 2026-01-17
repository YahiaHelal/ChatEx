package com.yahia.anotherchatapplicatoin.ui.controllers;
import com.yahia.anotherchatapplicatoin.protocol.server.ServerConnectionContext;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActiveServersSceneController implements ActiveServersSceneListener {
    private final ObservableList<ServerConnectionContext> serversList = FXCollections.observableArrayList();
    private final SceneNavigator navigator;


    public ActiveServersSceneController(SceneNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public ObservableList<ServerConnectionContext> getServersList() {
        return this.serversList;
    }

    @Override
    public void addServer(ServerConnectionContext connectionContext) {
        if(!serversList.contains(connectionContext)) {
            serversList.add(connectionContext);
        }
    }

    @Override
    public void onReturnButtonClicked() {
        navigator.showLoginScene();
    }
}
