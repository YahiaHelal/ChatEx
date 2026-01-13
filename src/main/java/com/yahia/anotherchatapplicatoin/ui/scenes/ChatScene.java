package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ChatSceneListener;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;


public class ChatScene extends AbstractChatScene{
    private TextArea chatTextArea;
    private Button sendButton;
    private Button logoutButton;
    private Button attachButton;
    private Button returnButton;
    private ImageView attachIcon;
    private ImageView returnIcon;
    private BorderPane root;
    private Scene chatScene;
    private TextField messageTextField;
    private HBox bottomBar;
    private HBox topBar;
    private Region spacer;
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
        attachButton = new Button();
        returnButton = new Button();
        root = new BorderPane();
        spacer = new Region();
        bottomBar = new HBox(10, messageTextField, attachButton, sendButton);
        topBar = new HBox(10, returnButton, spacer, logoutButton);
        chatScene = new Scene(root, WIDTH, HEIGHT);
    }

    @Override
    protected void buildUi() {
        root.setCenter(chatTextArea);
        root.setBottom(bottomBar);
        root.setTop(topBar);
        attachButton.setGraphic(attachIcon);
        returnButton.setGraphic(returnIcon);

    }
    @Override
    protected void loadResources() {
        attachIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/attach.png"))));
        returnIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/return.png"))));
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

        attachIcon.setFitHeight(16);
        attachIcon.setFitWidth(16);

        returnIcon.setFitWidth(16);
        returnIcon.setFitHeight(16);

        attachButton.setTooltip(new Tooltip("Attach File"));
        returnButton.setTooltip(new Tooltip("Return To Login Window"));

        HBox.setHgrow(messageTextField, Priority.ALWAYS); // expand message input
        HBox.setHgrow(spacer, Priority.ALWAYS);
    }



}
