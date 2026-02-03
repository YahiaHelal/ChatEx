package com.yahia.chatio.ui.scenes.listeners;

import com.yahia.chatio.protocol.server.ServerConnection;
import javafx.collections.ObservableList;

public interface ActiveServersSceneListener {
    ObservableList<ServerConnection> getServersList();
    void addServer(ServerConnection connectionContext);
    void onReturnButtonClicked();
}
