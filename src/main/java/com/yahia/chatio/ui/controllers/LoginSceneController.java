package com.yahia.chatio.ui.controllers;


import com.yahia.chatio.client.Client;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
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

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSceneController implements LoginSceneListener {
    private static final int MDNS_LOOKUP_RETRIES = 10;
    private static final long MDNS_LOOKUP_RETRY_DELAY = 250L;

    private final Logger LOGGER = LogManager.getLogger();
    private final MdnsDiscovery discovery;
    private final SceneNavigator navigator;

    private Client client;

    public  LoginSceneController(SceneNavigator navigator, MdnsDiscovery discovery) {
        this.navigator = navigator;
        this.discovery = discovery;
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
    public void onLoginButtonClicked(String username, String serverName) {
        new Thread(() -> {
            try {
                InetSocketAddress addr = awaitServerAddress(serverName);
                if(addr == null || addr.getAddress() == null) {
                    throw new IllegalStateException("server address is not yet resolved");
                }
                String ipAddress = addr.getAddress().toString();
                int port = addr.getPort();
                client = new Client(username, sanitizeIpAddress(ipAddress), port);
                initializeHandShakeListener();
                sendHandShake();
            }catch(Exception e) {
                LOGGER.log(Level.SEVERE, String.format("Client %s couldn't reach the server %s", username, serverName));
                Platform.runLater(() -> AlertUtils.warn(ConnectionStatus.REJECT_IO.message(), "Login Failed").showAndWait());
            }
        }, "login-connection-thread").start();

    }

    @Override
    public void onConnectedServersButtonClicked() {
        navigator.showActiveServersScene(navigator, client);
    }

    @Override
    public void onLaunchServerButtonClicked() {
        navigator.showServerLifeCycleScene();
    }

    private String sanitizeIpAddress(String ipAddress) {
        if (ipAddress == null) {
            return null;
        }
        return ipAddress.startsWith("/") ? ipAddress.substring(1) : ipAddress;
    }

    private InetSocketAddress awaitServerAddress(String serverName) throws InterruptedException{
        InetSocketAddress addr = null;
        for(int i = 0; i < MDNS_LOOKUP_RETRIES; i++) {
            addr = discovery.getServerAddress(serverName);
            if(addr != null && addr.getAddress() != null) {
                return addr;
            }
            Thread.sleep(MDNS_LOOKUP_RETRY_DELAY);
        }
        return addr;
    }
}
