package com.yahia.anotherchatapplicatoin.controllers;


import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController {
    private final LoginScene loginScene;
    public LoginSceneController(LoginScene loginScene, Stage stage) {
       this.loginScene = loginScene;
       setUpLoginButton(stage);
    }

    private void setUpLoginButton(Stage stage) {
        loginScene.getLoginButton().setOnAction(actionEvent -> {
            try {
                stage.setScene(new ChatScene(loginScene.getIpAddressText(), loginScene.getPortField(), loginScene.getUserNameField()).getScene());
            }catch (IOException e) {
                UiUtils.createAlert(Alert.AlertType.ERROR, "no server with that ip is currently running", "Failed to connect to server").showAndWait();
            }
        });
    }

}
