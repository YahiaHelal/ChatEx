package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
import com.yahia.chatio.ui.scenes.ActiveServersScene;
import com.yahia.chatio.ui.scenes.ChatScene;
import com.yahia.chatio.ui.scenes.LoginScene;
import com.yahia.chatio.ui.scenes.ServerLifeCycleScene;
import com.yahia.chatio.ui.scenes.listeners.ActiveServersSceneListener;

import java.io.IOException;


public interface SceneFactory {
    LoginScene createLoginScene();
    ChatScene createChatScene(Client client);
    ActiveServersScene createActiveServersScene(SceneNavigator navigator, Client client);
    ServerLifeCycleScene createServerLifeCycleScene(MdnsDiscovery discovery);
    ActiveServersSceneListener getServersController();
}
