package com.yahia.anotherchatapplicatoin.ui.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;

import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.ChatSceneListener;
import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.ServerEventsListener;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.BroadCastMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatSceneController implements ChatSceneListener, ServerEventsListener {
    private final Client client;
    private final TextArea chatArea;
    private final TextField inputField;
    private static final Logger LOGGER = LogManager.getLogger();
    private final SceneNavigator navigator;

    //TODO: ui-backend glue should be handled using a single controller
    public ChatSceneController(SceneNavigator navigator, TextArea chatArea, TextField inputField, Client client) {
        this.navigator = navigator;
        this.chatArea = chatArea;
        this.inputField = inputField;
        this.client = client;
        initializeMessageListener();
    }

    public void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

    @Override
    public void onSceneShown() {
        greetClient();
    }

    @Override
    public void onSendButtonClicked() {
        String msg = inputField.getText();
        if(!msg.isBlank()) {
            sendMessage(msg);
            inputField.clear();
        }
    }

    @Override
    public void onWindowClosed() {
        sendDisconnectRequest();
        client.closeClientSocket();
        //TODO: navigate to the login scene
    }

    @Override
    public void onUserExit() {
        sendDisconnectRequest();
        client.closeClientSocket();
        navigator.showLoginScene();
    }

    private void initializeMessageListener() {
        client.setMessageListener(this::onMessageReceived);
    }

    //TODO: handle client.sendMessage

    private void sendMessage(String message) {
        BroadCastMessage broadCastMessage = new BroadCastMessage(client.getClientName(), message);
        CommunicationPacket broadCastPacket = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(broadCastMessage));
        try {
            client.sendMessage(JsonHelper.GSON.toJson(broadCastPacket));
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            AlertUtils.createAlert(Alert.AlertType.ERROR, "Message has not been sent", "Failed to connect to server").showAndWait();
        }
    }
    private void greetClient() {
        BroadCastMessage msg = new BroadCastMessage("SERVER", String.format("%s Has Joined The Chat Room, Greet the hell out of em", client.getClientName()));
        CommunicationPacket packet = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(msg));
        try {
            client.sendMessage(JsonHelper.GSON.toJson(packet));
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            AlertUtils.createAlert(Alert.AlertType.ERROR, "Server has been shut down", "Failed to connect to server").showAndWait();
        }
    }

    private void sendDisconnectRequest() {
        String info = JsonHelper.GSON.toJson(new DisconnectRequest(client.getClientName()));
        try {
            client.sendMessage(JsonHelper.GSON.toJson(new CommunicationPacket(MessageType.DISCONNECT_REQUEST, info)));
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void onServerShutDown() {
        navigator.showLoginScene();
    }
}
