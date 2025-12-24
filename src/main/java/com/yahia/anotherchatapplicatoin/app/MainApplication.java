package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.ui.managers.DefaultSceneFactory;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneFactory;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneManager;
import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.text.DefaultEditorKit;

public class MainApplication extends Application {
    @Override

    public void start(Stage stage){
        DefaultSceneFactory sceneFactory = new DefaultSceneFactory(stage);
        SceneManager sceneManager = new SceneManager(stage, sceneFactory);
        sceneFactory.setNavigator(sceneManager);
        sceneManager.showLoginScene();
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}