package com.yahia.anotherchatapplicatoin.ui.controllers;

import com.yahia.anotherchatapplicatoin.client.Client;

import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectReason;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ChatSceneListener;
import com.yahia.anotherchatapplicatoin.ui.managers.SceneNavigator;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Logger;


public class ChatSceneController implements ChatSceneListener {
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
        initListeners();
    }

    public void onMessageReceived(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }

    @Override
    public void onSceneShown() {
        client.broadCastSystemMessage(String.format("%s Has Joined The Chat Room, Greet the hell out of em", client.getClientName()));
    }

    @Override
    public void onSendButtonClicked() {
        String msg = inputField.getText();
        if(!msg.isBlank()) {
            client.broadCastUserMessage(msg);
            inputField.clear();
        }
    }

    @Override
    public void onReturnButtonClicked() {
        navigator.showLoginScene();
    }

    @Override
    public void onWindowClosed() {
        client.disconnect(DisconnectReason.WINDOW_CLOSED);
    }

    @Override
    public void onUserExit() {
        client.disconnect(DisconnectReason.USER_LOGOUT);
    }

    private void initListeners() {
        client.setMessageHandler(this::onMessageReceived);
        client.setServerEventsListener(this::onDisconnect);
    }


    //TODO: a better way ?
    public void onDisconnect(DisconnectReason reason) {
        Platform.runLater(() -> {
            switch (reason) {
                case SERVER_SHUTDOWN -> {
                    AlertUtils.error("Server has been shut down", "Failed to connect to server").showAndWait();
                    navigator.showLoginScene();
                }
                case USER_LOGOUT -> {
                    navigator.showLoginScene();
                }
                case WINDOW_CLOSED, HANDSHAKE_FAILED -> {
                    // left blank in purpose
                }
            }
        });
    }
}
