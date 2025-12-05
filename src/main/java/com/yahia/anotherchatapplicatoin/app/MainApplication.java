package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.application.Application;
import javafx.scene.control.Alert;
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