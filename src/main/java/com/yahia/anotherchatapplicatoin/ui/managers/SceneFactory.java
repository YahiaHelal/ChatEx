package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;


public interface SceneFactory {
    LoginScene createLoginScene();
    ChatScene createChatScene(Client client);
}
