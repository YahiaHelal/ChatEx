package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.server.Server;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage){
        Server chatServer = new Server(8080);
        chatServer.start();
        stage.setScene(new LoginScene(stage).getScene());
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}