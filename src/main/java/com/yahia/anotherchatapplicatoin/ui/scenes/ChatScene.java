package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ChatSceneListener;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;


public class ChatScene extends AbstractChatScene{
    private TextArea chatTextArea;
    private Button sendButton;
    private Button logoutButton;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private HBox topBar;
    private ChatSceneListener chatSceneListener;
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

        sendButton.setOnAction(actionEvent -> {
            chatSceneListener.onSendButtonClicked();
        });


        stage.setOnCloseRequest(windowEvent -> {
            Optional<ButtonType> result = AlertUtils.confirm("Are you sure you want to quit ?", "Exit").showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                chatSceneListener.onWindowClosed();
            }else {
                windowEvent.consume();
            }
        });

        logoutButton.setOnAction(actionEvent -> {
            Optional<ButtonType> result =  AlertUtils.confirm("Are you sure you want to logout ?", "Logout").showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                chatSceneListener.onUserExit();
            }
        });
    }


    //TODO: server event listener
    @Override
    public void wireController(ChatSceneListener chatListener, Stage stage) {
        this.chatSceneListener = chatListener;
        setUpActions(stage);
    }

    @Override
    protected void initControls() {
        chatTextArea = new TextArea();
        messageTextField = new TextField();
        messageTextField.setPromptText("send your friends an insult to greet them");
        sendButton = new Button("Send");
        logoutButton = new Button("Logout");
        root = new BorderPane();
        bottomBar = new HBox(10, messageTextField, sendButton);
        topBar = new HBox(logoutButton);
        chatScene = new Scene(root, WIDTH, HEIGHT);
    }

    @Override
    protected void buildUi() {
        root.setCenter(chatTextArea);
        root.setBottom(bottomBar);
        root.setTop(topBar);
    }

    @Override
    protected void applyConstraints() {
        root.setPadding(new Insets(10));

        chatTextArea.setEditable(false);
        chatTextArea.setWrapText(true);

        bottomBar.setPadding(new Insets(10));
        topBar.setPadding(new Insets(5, 10, 5, 10));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        sendButton.setPrefWidth(100);
        logoutButton.setFocusTraversable(false);

        HBox.setHgrow(messageTextField, Priority.ALWAYS); // expand message input
    }



}
