package com.yahia.chatio.app;

import com.yahia.chatio.network.mdns.MdnsDiscovery;
import com.yahia.chatio.ui.managers.DefaultSceneFactory;
import com.yahia.chatio.ui.managers.SceneManager;
import com.yahia.chatio.utils.logging.LogManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication extends Application {
    @Override

    public void start(Stage stage){

        MdnsDiscovery discovery = new MdnsDiscovery();
        try {
            discovery.start();
        }catch (Exception e) {
            System.out.printf(e.getMessage());
        }
        DefaultSceneFactory sceneFactory = new DefaultSceneFactory(stage, discovery);
        SceneManager sceneManager = new SceneManager(stage, sceneFactory, discovery);
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