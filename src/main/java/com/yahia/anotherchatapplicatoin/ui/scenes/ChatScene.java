package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.protocol.MessageType;
import com.yahia.anotherchatapplicatoin.ui.controllers.ChatSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.ChatSceneListener;
import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.ServerEventsListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class ChatScene extends AbstractChatScene{
    private TextArea chatTextArea;
    private Button sendButton;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private ChatSceneListener chatSceneListener;
    private ServerEventsListener serverEventsListener;
    private final int WIDTH = 880, HEIGHT = 550;


    public ChatScene(){
        init();
    }

    public Scene getScene() {
        return chatScene;
    }
    public TextArea getChatTextArea() {
        return chatTextArea;
    }

    public TextField getMessageTextField() {
        return messageTextField;
    }


    @Override
    public void setUpActions(Stage stage) {
        chatSceneListener.onSceneShown();

        stage.setOnCloseRequest(windowEvent -> {
            chatSceneListener.onSceneClosed();
        });

        sendButton.setOnAction(actionEvent -> {
            chatSceneListener.onSendButtonClicked();
        });

        //TODO: onServerShutDown()
    }


    //TODO: called by the Factory
    //TODO: takes new ChatSceneController(client)
    @Override
    public void wireController(ChatSceneListener listener, Stage stage) {
        this.chatSceneListener = listener;
        setUpActions(stage);
    }

    @Override
    protected void initControls() {
        chatTextArea = new TextArea();
        messageTextField = new TextField();
        messageTextField.setPromptText("send your friends an insult to greet them");
        sendButton = new Button("Send");
        root = new BorderPane();
        bottomBar = new HBox(10, messageTextField, sendButton);
        chatScene = new Scene(root, WIDTH, HEIGHT);
    }

    @Override
    protected void buildUi() {
        root.setCenter(chatTextArea);
        root.setBottom(bottomBar);
    }

    @Override
    protected void applyConstraints() {
        root.setPadding(new Insets(10));
        chatTextArea.setEditable(false);
        chatTextArea.setWrapText(true);
        bottomBar.setPadding(new Insets(10));
        sendButton.setPrefWidth(100);
        HBox.setHgrow(messageTextField, Priority.ALWAYS); // expand message input
    }



}
