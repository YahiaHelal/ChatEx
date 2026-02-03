package com.yahia.chatio.ui.controllers;


import com.yahia.chatio.client.Client;
import com.yahia.chatio.protocol.codec.payload.json.handshake.JsonHandshakeRequestEncoder;
import com.yahia.chatio.protocol.disconnect.DisconnectReason;
import com.yahia.chatio.protocol.handshake.ConnectionStatus;
import com.yahia.chatio.protocol.packet.CommunicationPacket;
import com.yahia.chatio.protocol.packet.PacketType;
import com.yahia.chatio.ui.scenes.listeners.LoginSceneListener;
import com.yahia.chatio.ui.managers.SceneNavigator;
import com.yahia.chatio.utils.alerts.AlertUtils;
import com.yahia.chatio.utils.logging.LogManager;
import com.yahia.chatio.protocol.handshake.HandshakeRequest;
import javafx.application.Platform;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSceneController implements LoginSceneListener {
    private final Logger LOGGER = LogManager.getLogger();
    private final SceneNavigator navigator;

    private Client client;

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
                client.disconnect(DisconnectReason.HANDSHAKE_FAILED);
                AlertUtils.warn("Login Failed", status.message()).showAndWait();
            }
        });
    }


    private void sendHandShake() {
        JsonHandshakeRequestEncoder encoder = new JsonHandshakeRequestEncoder();
        String username = encoder.encode(new HandshakeRequest(client.getClientName()));
        client.sendMessage(new CommunicationPacket(PacketType.HANDSHAKE_REQUEST, username));
    }

    @Override
    public void onLoginButtonClicked(String username, String ipAddress, String port) {
        try {
            client = new Client(username, ipAddress, Integer.parseInt(port));
            initializeHandShakeListener();
            sendHandShake();
        }catch(Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Client %s couldn't reach the server %s:%s", username, ipAddress, port));
            AlertUtils.warn(ConnectionStatus.REJECT_IO.message(), "Login Failed").showAndWait();
        }
    }

    @Override
    public void onConnectedServersButtonClicked() {
        navigator.showActiveServersScene(navigator, client);
    }

    @Override
    public void onLaunchServerButtonClicked() {
        navigator.showServerLauncherScene();
    }
}
