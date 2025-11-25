package com.yahia.anotherchatapplicatoin.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ChatSceneController {

    private Client client;
    private final TextArea chatArea;
    private final Button sendButton;
    private final TextField inputField;

    //TODO: ui-backend glue should be handled using a single controller
    public ChatSceneController(TextArea chatArea, Button sendButton, TextField inputField) {
        this.chatArea = chatArea;
        this.sendButton = sendButton;
        this.inputField = inputField;
        setUpSendButton();
    }



    public void connect(String serverIp, int port, String username) throws IOException {
        client = new Client(serverIp, port, username);
        client.setMessageListener(this::onMessageReceived);
    }

    private void setUpSendButton() {
        sendButton.setOnAction(actionEvent -> {
            String msg = inputField.getText();
            if(!msg.isBlank()) {
                client.sendMessage(msg);
                inputField.clear();
            }
        });
    }

    private String prefixMessage(String message) {
        return String.format("[%s]: %s\n", client.getClientName(), message);
    }

    private void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(prefixMessage(msg)));
    }

}
