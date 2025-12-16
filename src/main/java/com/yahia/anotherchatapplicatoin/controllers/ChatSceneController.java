package com.yahia.anotherchatapplicatoin.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.BroadCastMessage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.logging.Logger;


public class ChatSceneController {
    private final Stage stage;
    private final Client client;
    private final TextArea chatArea;
    private final Button sendButton;
    private final TextField inputField;
    private static final Logger LOGGER = LogManager.getLogger();

    //TODO: ui-backend glue should be handled using a single controller
    public ChatSceneController(TextArea chatArea, Button sendButton, TextField inputField, Client client, Stage stage) {
        this.chatArea = chatArea;
        this.sendButton = sendButton;
        this.inputField = inputField;
        this.client = client;
        this.stage = stage;
        initializeMessageListener();
        setUpSendButton();
        setUpOnClosing();
    }


    private void initializeMessageListener() {
        client.setMessageListener(this::onMessageReceived);
    }
    private void sendMessage(String message) {
        BroadCastMessage broadCastMessage = new BroadCastMessage(client.getClientName(), message);
        CommunicationPacket broadCastPacket = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(broadCastMessage));
        client.sendMessage(JsonHelper.GSON.toJson(broadCastPacket));
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
    private void setUpOnClosing() {
        stage.setOnCloseRequest(windowEvent -> {
            sendDisconnectRequest();
            client.closeClientSocket();
        });
    }
    private void sendDisconnectRequest() {
        String info = JsonHelper.GSON.toJson(new DisconnectRequest(client.getClientName()));
        client.sendMessage(JsonHelper.GSON.toJson(new CommunicationPacket(MessageType.DISCONNECT_REQUEST, info)));
    }

    public void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

}
