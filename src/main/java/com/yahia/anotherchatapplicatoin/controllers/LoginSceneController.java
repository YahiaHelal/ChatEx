package com.yahia.anotherchatapplicatoin.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.controllers.listeners.LoginSceneListener;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeRequest;
import com.yahia.anotherchatapplicatoin.scenes.ChatScene;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginSceneController implements LoginSceneListener {
    private ChatScene chatScene;
    private final Stage STAGE;
    private Client client;

    public LoginSceneController(Stage stage) {
       this.STAGE = stage;
    }

    private void switchToChatScene() {
        chatScene = new ChatScene(STAGE, client);
        STAGE.setScene(chatScene.getScene());
        chatScene.onShown();
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
                UiUtils.createAlert(Alert.AlertType.WARNING, "Login Failed", status.message()).showAndWait();
            }
        });
    }
    private void sendHandShake() {
        String username = JsonHelper.GSON.toJson(new HandShakeRequest(client.getClientName()));
        CommunicationPacket handShakePacket = new CommunicationPacket(MessageType.HANDSHAKE_REQUEST, username);
        client.sendMessage(JsonHelper.GSON.toJson(handShakePacket));
    }


    @Override
    public void onLoginButtonClicked(String username, String ipAddress, int port) {
        client = new Client(username, ipAddress, port);
        initializeHandShakeListener();
        sendHandShake();
    }
}
