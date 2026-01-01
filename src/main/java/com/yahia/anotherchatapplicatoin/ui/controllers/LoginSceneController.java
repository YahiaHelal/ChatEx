package com.yahia.anotherchatapplicatoin.ui.controllers;


import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectReason;
import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.LoginSceneListener;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeRequest;
import javafx.application.Platform;

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
                client.disconnect(DisconnectReason.HANDSHAKE_FAILED);
                AlertUtils.warn("Login Failed", status.message()).showAndWait();
            }
        });
    }
    //TODO: handle send message
    private void sendHandShake() {
        String username = JsonHelper.GSON.toJson(new HandshakeRequest(client.getClientName()));
        CommunicationPacket handShakePacket = new CommunicationPacket(PacketType.HANDSHAKE_REQUEST, username);
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
            AlertUtils.warn(ConnectionStatus.REJECT_IO.message(), "Login Failed").showAndWait();
        }
    }
}
