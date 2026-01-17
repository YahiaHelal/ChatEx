package com.yahia.anotherchatapplicatoin.ui.scenes.listeners;

import com.yahia.anotherchatapplicatoin.protocol.server.ServerConnectionContext;
import javafx.collections.ObservableList;

public interface ActiveServersSceneListener {
    ObservableList<ServerConnectionContext> getServersList();
    void addServer(ServerConnectionContext connectionContext);
    void onReturnButtonClicked();
}
