package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;

public interface SceneNavigator {
    void showLoginScene();
    void showChatScene(Client client);
    void showActiveServersScene(SceneNavigator navigator, Client client);
    SceneFactory getFactory();
}
