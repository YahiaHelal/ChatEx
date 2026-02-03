package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;

public interface SceneNavigator {
    void showLoginScene();
    void showChatScene(Client client);
    void showActiveServersScene(SceneNavigator navigator, Client client);
    void showServerLauncherScene();
    SceneFactory getFactory();
}
