package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.controllers.ChatSceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class ChatScene {
    private TextArea chatTextArea;
    private Button sendButton;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private ChatSceneController controller;
    private final int WIDTH = 880, HEIGHT = 550;
    private final Stage STAGE;


    public ChatScene(Stage stage, Client client){
        this.STAGE = stage;
        initControls();
        applyConstraints();
        buildUi();
        initController(client);
    }

    public Scene getScene() {
        return chatScene;
    }

    public void onShown() {
        controller.onSceneShown();
    }

    private void initController(Client client){
        controller = new ChatSceneController(chatTextArea, sendButton, messageTextField, client);
        STAGE.setOnCloseRequest(windowEvent -> {
            controller.onSceneClosed();
        });
    }
    private void initControls() {
        chatTextArea = new TextArea();
        messageTextField = new TextField();
        messageTextField.setPromptText("send your friends an insult to greet them");
        sendButton = new Button("Send");
        root = new BorderPane();
        bottomBar = new HBox(10, messageTextField, sendButton);
        chatScene = new Scene(root, WIDTH, HEIGHT);
    }

    private void buildUi() {
        root.setCenter(chatTextArea);
        root.setBottom(bottomBar);
    }

    private void applyConstraints() {
        root.setPadding(new Insets(10));
        chatTextArea.setEditable(false);
        chatTextArea.setWrapText(true);
        bottomBar.setPadding(new Insets(10));
        sendButton.setPrefWidth(100);
        HBox.setHgrow(messageTextField, Priority.ALWAYS); // expand message input
    }

}
