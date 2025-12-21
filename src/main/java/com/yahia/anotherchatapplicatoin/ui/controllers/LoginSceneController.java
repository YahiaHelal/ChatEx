package com.yahia.anotherchatapplicatoin.ui.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.LoginSceneListener;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeRequest;
import com.yahia.anotherchatapplicatoin.ui.scenes.ChatScene;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSceneController implements LoginSceneListener {
    private ChatScene chatScene;
    private final Stage STAGE;
    private Client client;
    private static final Logger LOGGER = LogManager.getLogger();

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
                AlertUtils.createAlert(Alert.AlertType.INFORMATION, "Logged In", status.message()).showAndWait();
                switchToChatScene();
            }else {
                AlertUtils.createAlert(Alert.AlertType.WARNING, "Login Failed", status.message()).showAndWait();
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
        try {
            client = new Client(username, ipAddress, port);
            initializeHandShakeListener();
            sendHandShake();
        }catch(IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Client %s couldn't reach the server %s:%d", username, ipAddress, port));
            AlertUtils.createAlert(Alert.AlertType.WARNING, String.format("there is no running server at %s:%d", ipAddress, port), "Login Failed").showAndWait();
        }
    }
}
