package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;
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
    private final Stage STAGE;


    public ChatScene(Stage stage, Client client){
        this.STAGE = stage;
        init(client);
    }

    public Scene getScene() {
        return chatScene;
    }


    public void onShown() {
        chatSceneListener.onSceneShown();
    }

    public void switchToLoginScene() {

    }

    @Override
    protected void initController(Client client){
        chatSceneListener = new ChatSceneController(chatTextArea, messageTextField, client);
        STAGE.setOnCloseRequest(windowEvent -> {
            chatSceneListener.onSceneClosed();
        });
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

    @Override
    protected void setUpActions() {
        sendButton.setOnAction(actionEvent -> {
            chatSceneListener.onSendButtonClicked();
        });
        serverEventsListener.onServerShutDown();
    }

}
