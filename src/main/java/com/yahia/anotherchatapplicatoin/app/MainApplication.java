package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.ui.managers.DefaultSceneFactory;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override

    public void start(Stage stage){
        DefaultSceneFactory sceneFactory = new DefaultSceneFactory(stage);
        SceneManager sceneManager = new SceneManager(stage, sceneFactory);
        sceneFactory.setNavigator(sceneManager);
        sceneFactory.wireServersSceneController();
        sceneManager.showLoginScene();
        stage.setTitle("ChatIO!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}