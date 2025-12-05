package com.yahia.anotherchatapplicatoin.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.protocol.ConnectionStatus;
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

    private void setUpLoginButton(Stage stage) {
        loginScene.getLoginButton().setOnAction(actionEvent -> {

            ConnectionStatus status = Client.handShake(loginScene.getIpAddress(), loginScene.getPort(), loginScene.getUsername());

            if (status == ConnectionStatus.ACCEPT) {
                client = new Client(loginScene.getUsername());
                client.connectAndStart(loginScene.getIpAddress(), loginScene.getPort());
                UiUtils.createAlert(Alert.AlertType.INFORMATION,
                        "Logged in!",
                        status.message()).showAndWait();

                stage.setScene(
                        new ChatScene(
                                loginScene.getIpAddress(),
                                loginScene.getPort(),
                                loginScene.getUsername(),
                                client
                        ).getScene()
                );
            } else {
                UiUtils.createAlert(
                        Alert.AlertType.WARNING,
                        "Login Failed",
                        status.message()
                ).showAndWait();
            }


        });
    }

}
