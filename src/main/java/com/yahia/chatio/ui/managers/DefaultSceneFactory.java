package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
import com.yahia.chatio.ui.controllers.ActiveServersSceneController;
import com.yahia.chatio.ui.controllers.ChatSceneController;
import com.yahia.chatio.ui.controllers.LoginSceneController;
import com.yahia.chatio.ui.controllers.ServerLifeCycleController;
import com.yahia.chatio.ui.scenes.ActiveServersScene;
import com.yahia.chatio.ui.scenes.ChatScene;
import com.yahia.chatio.ui.scenes.LoginScene;
import com.yahia.chatio.ui.scenes.ServerLifeCycleScene;
import com.yahia.chatio.ui.scenes.listeners.ActiveServersSceneListener;
import javafx.stage.Stage;

public class DefaultSceneFactory implements SceneFactory{
    private final Stage stage;
    private ActiveServersSceneListener serversSceneController;
    private SceneNavigator navigator;
    private final MdnsDiscovery discovery;

    public DefaultSceneFactory(Stage stage, MdnsDiscovery discovery) {
        this.stage = stage;
        this.discovery = discovery;
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
        loginScene.wireController(new LoginSceneController(navigator, discovery));
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
    public ServerLifeCycleScene createServerLifeCycleScene(MdnsDiscovery discovery) {
        ServerLifeCycleScene launcherScene = new ServerLifeCycleScene();
        launcherScene.wireController(new ServerLifeCycleController(discovery));
        return launcherScene;
    }
}
