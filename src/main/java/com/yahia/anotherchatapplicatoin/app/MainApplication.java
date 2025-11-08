package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage){
        LoginScene loginScene =  new LoginScene();
        ChatScene chatScene = new ChatScene();
        stage.setScene(loginScene.getScene());

        Button loginButton = loginScene.getLoginButton();
        loginButton.setOnAction(actionEvent -> {
            stage.setScene(chatScene.getScene());
        });
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}