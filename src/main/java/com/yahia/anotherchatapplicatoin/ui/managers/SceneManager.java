package com.yahia.anotherchatapplicatoin.ui.managers;

import com.yahia.anotherchatapplicatoin.client.Client;
import javafx.stage.Stage;

public class SceneManager implements SceneNavigator {
    private final Stage stage;
    private final SceneFactory sceneFactory;

    public SceneManager(Stage stage, SceneFactory factory) {
        this.stage = stage;
        this.sceneFactory = factory;
    }
    @Override
    public void showChatScene(Client client) {
        stage.setScene(sceneFactory.createChatScene(client).getScene());
    }

    @Override
    public void showLoginScene() {
        stage.setScene(sceneFactory.createLoginScene().getScene());
    }
}
