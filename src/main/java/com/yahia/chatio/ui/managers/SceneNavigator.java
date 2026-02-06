package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;
import com.yahia.chatio.network.mdns.MdnsDiscovery;

public interface SceneNavigator {
    void showLoginScene();
    void showChatScene(Client client);
    void showActiveServersScene(SceneNavigator navigator, Client client);
    void showServerLifeCycleScene();
    SceneFactory getFactory();
}
