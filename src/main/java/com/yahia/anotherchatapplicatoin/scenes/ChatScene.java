package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.Utils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class ChatScene {
    private TextArea chatTextArea;
    private Button sendButton;
    private GridPane chatGrid;
    private Scene chatScene;
    private VBox messagesBox;

    private void initControls() {
        chatTextArea = new TextArea();
        sendButton = new Button("Send");
        chatGrid = new GridPane();
        messagesBox = new VBox();
        chatScene = new Scene(chatGrid);
    }

    private void buildUi() {
        chatGrid.add(messagesBox, 0, 0);
        chatGrid.add(sendButton, 0, 1);
        chatGrid.add(chatTextArea, 1, 1);
        Utils.setGridSpacing(chatGrid);
        Utils.setVBoxSpacing(messagesBox);

    }

    private void addActions() {
        sendButton.setOnAction(actionEvent -> {
            String text = chatTextArea.getText();
            messagesBox.getChildren().add(new Label(text));
            System.out.println(messagesBox.getChildren().size());
        });
    }
    public Scene getScene() {
        return chatScene;
    }
    public ChatScene() {
        initControls();
        addActions();
        buildUi();
    }
}
