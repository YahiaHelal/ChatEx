package com.yahia.anotherchatapplicatoin.ui.managers.ui;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;
import javafx.stage.Stage;

public class SceneManager implements SceneNavigator {
    private final Stage stage;
    private final SceneFactory sceneFactory;

    public SceneManager(Stage stage, SceneFactory factory) {
        this.stage = stage;
        this.sceneFactory = factory;
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
    public void showServerLauncherScene() {
        stage.setScene(sceneFactory.createServerLauncherScene().getScene());
    }

    @Override
    public void showLoginScene() {
        stage.setScene(sceneFactory.createLoginScene().getScene());
    }
}
