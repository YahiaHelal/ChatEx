package com.yahia.chatio.ui.controllers;

import com.yahia.chatio.client.Client;

import com.yahia.chatio.protocol.disconnect.DisconnectReason;
import com.yahia.chatio.ui.scenes.listeners.ChatSceneListener;
import com.yahia.chatio.ui.managers.SceneNavigator;
import com.yahia.chatio.utils.alerts.AlertUtils;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatSceneController implements ChatSceneListener {
    private final Client client;
    private final TextArea chatArea;
    private final TextField inputField;
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

    //NOTE: was an option before GUI
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
        client.setDisconnectListener(this::onDisconnect);
    }


    //TODO: a better way ?
    public void onDisconnect(DisconnectReason reason) {
        Platform.runLater(() -> {
            switch (reason) {
                case SERVER_SHUTDOWN -> {
                    AlertUtils.error("Server has been shut down", "Failed to connect to server").showAndWait();
                    navigator.showLoginScene();
                }
                case USER_LOGOUT -> navigator.showLoginScene();
            }
        });
    }
}
