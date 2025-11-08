package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.utils.LayoutUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


public class ChatScene {
    private TextArea chatTextArea;
    private Button sendButton;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private final int WIDTH = 880, HEIGHT = 550;


    public ChatScene() {
        initControls();
        applyConstraints();
        addActions();
        buildUi();
    }

    public Scene getScene() {
        return chatScene;
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
            displayMessage(getInput());
            clearInput();
        });
    }

    private void displayMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }
    private void clearInput() {
        messageTextField.clear();
    }
    private String getInput() {
        return messageTextField.getText();
    }

}
