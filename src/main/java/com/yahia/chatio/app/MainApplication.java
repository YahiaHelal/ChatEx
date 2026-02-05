package com.yahia.chatio.app;

import com.yahia.chatio.network.mdns.MdnsAnnouncer;
import com.yahia.chatio.ui.managers.DefaultSceneFactory;
import com.yahia.chatio.ui.managers.SceneManager;
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