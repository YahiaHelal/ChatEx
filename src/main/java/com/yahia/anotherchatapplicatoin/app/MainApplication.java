package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage){
        Server chatServer = new Server(8080);
        chatServer.start();
        LoginScene loginScene = new LoginScene();
        loginScene.getLoginButton().setOnAction(actionEvent -> {
            try {
                new Client(loginScene.getIpAddressText(), loginScene.getPortField(), loginScene.getUserNameField());
                stage.setScene(new ChatScene(chatServer.getServerAddress(), chatServer.getServerPort(), loginScene.getUserNameField()).getScene());
            }catch (Exception e) {
                UiUtils.createAlert(Alert.AlertType.ERROR, "no server with that ip is currently running", "Failed to connect to server").showAndWait();
            }
        });
        stage.setScene(loginScene.getScene());
        stage.setTitle("Chat!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}