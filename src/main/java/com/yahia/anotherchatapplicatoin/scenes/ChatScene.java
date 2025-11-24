package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.controllers.ChatSceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.logging.Logger;


public class ChatScene {
    private TextArea chatTextArea;
    private Button sendButton;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private final int WIDTH = 880, HEIGHT = 550;


    public ChatScene(String ip, int port, String username) throws IOException{
        initController(ip, port, username);
        initControls();
        applyConstraints();
        addActions();
        buildUi();
    }

    public Scene getScene() {
        return chatScene;
    }

    private void initController(String ip, int port, String username) throws IOException{
        ChatSceneController controller = new ChatSceneController(chatTextArea, sendButton, messageTextField);
        controller.connect(ip, port, username);
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

    private void addActions() {

        sendButton.setOnAction(actionEvent -> {
            if(getInput().isEmpty()) return;

        });
    }

    private String getInput() {
        return messageTextField.getText();
    }

    //TODO: sets the message listener function for the client, check Platform.runLater()
    private void listenToUpdates() {

    }

}
