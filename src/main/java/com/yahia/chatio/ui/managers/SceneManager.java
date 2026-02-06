package com.yahia.chatio.ui.managers;

import com.yahia.chatio.client.Client;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
import javafx.stage.Stage;

public class SceneManager implements SceneNavigator {
    private final Stage stage;
    private final SceneFactory sceneFactory;
    private final MdnsDiscovery discovery;

    public SceneManager(Stage stage, SceneFactory factory, MdnsDiscovery discovery) {
        this.stage = stage;
        this.sceneFactory = factory;
        this.discovery = discovery;
    }

    public SceneFactory getFactory() {
        return this.sceneFactory;
    }

    @Override
    public void showChatScene(Client client) {
        stage.setScene(sceneFactory.createChatScene(client).getScene());
    }

    @Override
    public void showActiveServersScene(SceneNavigator navigator, Client client) {
        stage.setScene(sceneFactory.createActiveServersScene(navigator, client).getScene());
    }

    @Override
    public void showServerLifeCycleScene() {
        stage.setScene(sceneFactory.createServerLifeCycleScene(discovery).getScene());
    }

    @Override
    public void showLoginScene() {
        stage.setScene(sceneFactory.createLoginScene().getScene());
    }
}
