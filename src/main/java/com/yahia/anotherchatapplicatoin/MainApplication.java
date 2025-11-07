package com.yahia.anotherchatapplicatoin;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

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