package com.yahia.anotherchatapplicatoin.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginSceneController {
    private final LoginScene loginScene;
    private Client client;

    public LoginSceneController(LoginScene loginScene, Stage stage) {
       this.loginScene = loginScene;
       setUpLoginButton(stage);
    }
    private void connect(String serverIp, int port, String username) {
        client = new Client(serverIp, port, username);
    }
    private void setUpLoginButton(Stage stage) {
        loginScene.getLoginButton().setOnAction(actionEvent -> {
            try {
                connect(loginScene.getIpAddress(), loginScene.getPort(), loginScene.getUsername());
                stage.setScene(
                        new ChatScene(
                                loginScene.getIpAddress(),
                                loginScene.getPort(),
                                loginScene.getUsername(),
                                client
                        ).getScene()
                );
            }catch (NumberFormatException e) {
                UiUtils.createAlert(Alert.AlertType.ERROR, "no server with that ip is currently running", "Failed to connect to server").showAndWait();
            }
        });
    }

}
