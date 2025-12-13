package com.yahia.anotherchatapplicatoin.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginSceneController {
    private final LoginScene LOGIN_SCENE;
    private final Stage STAGE;
    private Client client;

    public LoginSceneController(LoginScene loginScene, Stage stage) {
       this.LOGIN_SCENE = loginScene;
       this.STAGE = stage;
       setUpLoginButton();
    }

    private void switchToChatScene() {
        STAGE.setScene(new ChatScene(LOGIN_SCENE.getIpAddress(), LOGIN_SCENE.getPort(), LOGIN_SCENE.getUsername(), client).getScene());
    }
    private void initializeHandShakeListener() {
        client.setHandShakeListener(this::onHandShakeStatusReceived);
    }
    private void onHandShakeStatusReceived(ConnectionStatus status) {
        Platform.runLater(() -> {
            if(status == ConnectionStatus.ACCEPT) {
                UiUtils.createAlert(Alert.AlertType.INFORMATION, "Logged In", status.message()).showAndWait();
                switchToChatScene();
            }else {
                UiUtils.createAlert(Alert.AlertType.ERROR, "Login Failed", status.message()).showAndWait();
            }
        });
    }
    private void sendHandShake() {
        String jsonUsername = JsonHelper.GSON.toJson(new HandShakeRequest(client.getClientName()));
        CommunicationPacket handShakePacket = new CommunicationPacket(MessageType.HANDSHAKE_REQUEST, jsonUsername);
        client.sendMessage(JsonHelper.GSON.toJson(handShakePacket));
    }

    private void setUpLoginButton() {
        LOGIN_SCENE.getLoginButton().setOnAction(actionEvent -> {
            client = new Client(LOGIN_SCENE.getUsername(), LOGIN_SCENE.getIpAddress(), LOGIN_SCENE.getPort());
            initializeHandShakeListener();
            sendHandShake();
        });
    }

}
