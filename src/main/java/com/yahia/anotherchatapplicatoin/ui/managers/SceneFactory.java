package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.scenes.ActiveServersScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ActiveServersSceneListener;


public interface SceneFactory {
    LoginScene createLoginScene();
    ChatScene createChatScene(Client client);
    ActiveServersScene createActiveServersScene(SceneNavigator navigator, Client client);
    ActiveServersSceneListener getServersController();
}
