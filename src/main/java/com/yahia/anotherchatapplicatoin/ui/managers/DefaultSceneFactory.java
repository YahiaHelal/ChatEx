package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.controllers.ChatSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.LoginSceneController;
import com.yahia.anotherchatapplicatoin.ui.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;
import javafx.stage.Stage;

public class DefaultSceneFactory implements SceneFactory{
    private final Stage stage;
    private SceneNavigator navigator;
    public DefaultSceneFactory(Stage stage) {
        this.stage = stage;
    }
    public void setNavigator(SceneNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public LoginScene createLoginScene() {
        LoginScene loginScene = new LoginScene();
        loginScene.wireController(new LoginSceneController(navigator), stage);
        return loginScene;
    }

    @Override
    public ChatScene createChatScene(Client client) {
        ChatScene chatScene = new ChatScene();
        ChatSceneController controller = new ChatSceneController(navigator, chatScene.getChatTextArea(), chatScene.getMessageTextField(), client);
        chatScene.wireController(controller, stage);
        return chatScene;
    }
}
