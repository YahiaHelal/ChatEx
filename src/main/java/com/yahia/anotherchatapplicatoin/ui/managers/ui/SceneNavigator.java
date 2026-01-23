package com.yahia.anotherchatapplicatoin.ui.managers.ui;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public interface SceneNavigator {
    void showLoginScene();
    void showChatScene(Client client);
    void showActiveServersScene(SceneNavigator navigator, Client client);
    void showServerLauncherScene();
    SceneFactory getFactory();
}
