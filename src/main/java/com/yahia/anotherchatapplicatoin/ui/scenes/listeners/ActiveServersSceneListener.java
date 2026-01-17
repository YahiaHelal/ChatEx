package com.yahia.anotherchatapplicatoin.ui.scenes.listeners;

import com.yahia.anotherchatapplicatoin.protocol.server.ClientConnection;
import javafx.collections.ObservableList;

public interface ActiveServersSceneListener {
    ObservableList<ClientConnection> getServersList();
    void addServer(ClientConnection connectionContext);
    void onReturnButtonClicked();
}
