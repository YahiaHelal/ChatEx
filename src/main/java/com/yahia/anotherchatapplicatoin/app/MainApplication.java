package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.ui.scenes.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override

    public void start(Stage stage){
        LoginScene loginScene = new LoginScene(stage);
        stage.setScene(loginScene.getScene());
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}