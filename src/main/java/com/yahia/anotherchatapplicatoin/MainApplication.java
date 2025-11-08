package com.yahia.anotherchatapplicatoin;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage){
        ChatScene chatScene = new ChatScene();
        stage.setScene(chatScene.getScene());
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}