package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;
import com.yahia.chatio.ui.scenes.ActiveServersScene;
import com.yahia.chatio.ui.scenes.ChatScene;
import com.yahia.chatio.ui.scenes.LoginScene;
import com.yahia.chatio.ui.scenes.ServerLauncherScene;
import com.yahia.chatio.ui.scenes.listeners.ActiveServersSceneListener;


public interface SceneFactory {
    LoginScene createLoginScene();
    ChatScene createChatScene(Client client);
    ActiveServersScene createActiveServersScene(SceneNavigator navigator, Client client);
    ServerLauncherScene createServerLauncherScene();
    ActiveServersSceneListener getServersController();
}
