package com.yahia.anotherchatapplicatoin.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.controllers.listeners.SceneClosedListener;
import com.yahia.anotherchatapplicatoin.controllers.listeners.SceneShownListener;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.BroadCastMessage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;


public class ChatSceneController implements SceneShownListener, SceneClosedListener {
    private final Client client;
    private final TextArea chatArea;
    private final Button sendButton;
    private final TextField inputField;
    private static final Logger LOGGER = LogManager.getLogger();

    //TODO: ui-backend glue should be handled using a single controller
    public ChatSceneController(TextArea chatArea, Button sendButton, TextField inputField, Client client) {
        this.chatArea = chatArea;
        this.sendButton = sendButton;
        this.inputField = inputField;
        this.client = client;
        initializeMessageListener();
        setUpSendButton();
    }


    private void initializeMessageListener() {
        client.setMessageListener(this::onMessageReceived);
    }
    private void sendMessage(String message) {
        BroadCastMessage broadCastMessage = new BroadCastMessage(client.getClientName(), message);
        CommunicationPacket broadCastPacket = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(broadCastMessage));
        client.sendMessage(JsonHelper.GSON.toJson(broadCastPacket));
    }
    private void greetClient() {
        BroadCastMessage msg = new BroadCastMessage("SERVER", String.format("%s Has Joined The Chat Room, Greet the hell out of em", client.getClientName()));
        CommunicationPacket packet = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(msg));
        client.sendMessage(JsonHelper.GSON.toJson(packet));
    }
    private void setUpSendButton() {
        sendButton.setOnAction(actionEvent -> {
            String msg = inputField.getText();
            if(!msg.isBlank()) {
                sendMessage(msg);
                inputField.clear();
            }
        });
    }
    private void sendDisconnectRequest() {
        String info = JsonHelper.GSON.toJson(new DisconnectRequest(client.getClientName()));
        client.sendMessage(JsonHelper.GSON.toJson(new CommunicationPacket(MessageType.DISCONNECT_REQUEST, info)));
    }

    public void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

    @Override
    public void onSceneShown() {
        greetClient();
    }

    @Override
    public void onSceneClosed() {
        sendDisconnectRequest();
        client.closeClientSocket();
    }
}
