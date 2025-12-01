package com.yahia.anotherchatapplicatoin.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;


public class ChatSceneController {

    private Client client;
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
        initializeListener();
        setUpSendButton();
    }


    private void initializeListener() {
        client.setMessageListener(this::onMessageReceived);
        client.sendMessage(String.format("%s Has Joined The Chat Room, Greet the hell out of em", client.getClientName()), true);
    }
    private void setUpSendButton() {
        sendButton.setOnAction(actionEvent -> {
            String msg = inputField.getText();
            if(!msg.isBlank()) {
                client.sendMessage(msg, false);
                inputField.clear();
            }
        });
    }

    public void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

}
