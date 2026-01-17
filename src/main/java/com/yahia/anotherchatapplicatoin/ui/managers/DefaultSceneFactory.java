package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.controllers.ActiveServersSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.ChatSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.LoginSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.ServerLauncherController;
import com.yahia.anotherchatapplicatoin.ui.scenes.ActiveServersScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.ServerLauncherScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DefaultSceneFactory implements SceneFactory{
    private final Stage stage;
    private ActiveServersSceneListener serversSceneController;
    private SceneNavigator navigator;
    public DefaultSceneFactory(Stage stage) {
        this.stage = stage;
    }
    public void setNavigator(SceneNavigator navigator) {
        this.navigator = navigator;
    }

    public void wireServersSceneController() {
        serversSceneController = new ActiveServersSceneController(navigator);
    }

    public ActiveServersSceneListener getServersController() {
        return this.serversSceneController;
    }

    @Override
    public LoginScene createLoginScene() {
        LoginScene loginScene = new LoginScene();
        loginScene.wireController(new LoginSceneController(navigator));
        return loginScene;
    }

    @Override
    public ChatScene createChatScene(Client client) {
        ChatScene chatScene = new ChatScene();
        ChatSceneController controller = new ChatSceneController(navigator, chatScene.getChatTextArea(), chatScene.getMessageTextField(), client);
        chatScene.wireController(controller, stage);
        return chatScene;
    }

    @Override
    public ActiveServersScene createActiveServersScene(SceneNavigator navigator, Client client) {
        ActiveServersScene serversScene = new ActiveServersScene();
        serversScene.wireController(this.serversSceneController);
        return serversScene;
    }

    @Override
    public ServerLauncherScene createServerLauncherScene() {
        ServerLauncherScene launcherScene = new ServerLauncherScene();
        launcherScene.wireController(new ServerLauncherController());
        return launcherScene;
    }
}
