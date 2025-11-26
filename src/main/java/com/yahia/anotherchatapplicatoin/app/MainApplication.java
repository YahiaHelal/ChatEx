package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    //TODO: re-write after setting up the controllers
    public void start(Stage stage){
        LoginScene loginScene = new LoginScene();
        loginScene.getLoginButton().setOnAction(actionEvent -> {
            try {
                stage.setScene(new ChatScene(loginScene.getIpAddressText(), loginScene.getPortField(), loginScene.getUserNameField()).getScene());
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