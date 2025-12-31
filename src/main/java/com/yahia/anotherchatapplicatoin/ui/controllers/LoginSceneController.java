package com.yahia.anotherchatapplicatoin.ui.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.LoginSceneListener;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeRequest;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSceneController implements LoginSceneListener {
    private final SceneNavigator navigator;
    private Client client;
    private static final Logger LOGGER = LogManager.getLogger();

    public  LoginSceneController(SceneNavigator navigator) {
        this.navigator = navigator;
    }


    private void initializeHandShakeListener() {
        client.setHandShakeHandler(this::onHandShakeStatusReceived);
    }
    private void onHandShakeStatusReceived(ConnectionStatus status) {
        Platform.runLater(() -> {
            if(status == ConnectionStatus.ACCEPT) {
                AlertUtils.info("Logged In", status.message()).showAndWait();
                navigator.showChatScene(client);
            }else {
                AlertUtils.warn("Login Failed", status.message()).showAndWait();
            }
        });
    }
    //TODO: handle send message
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
            AlertUtils.warn(String.format("there is no running server at %s:%d", ipAddress, port), "Login Failed").showAndWait();
        }
    }
}
