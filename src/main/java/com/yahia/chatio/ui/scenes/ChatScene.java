package com.yahia.chatio.ui.scenes;

import com.yahia.chatio.ui.scenes.base.AbstractChatScene;
import com.yahia.chatio.ui.scenes.listeners.ChatSceneListener;
import com.yahia.chatio.utils.alerts.AlertUtils;
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


public class ChatScene extends AbstractChatScene {
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
    private final int ICON_HEIGHT = 16, ICON_WIDTH = 16;


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
    public void wireController(ChatSceneListener chatListener, Stage stage) {
        this.chatSceneListener = chatListener;
        chatListener.onSceneShown();
        setUpActions(stage);
    }

    @Override
    protected void setupLoginButtonActions(Stage stage) {
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
    }

    @Override
    protected void setupLogoutButtonActions() {
        logoutButton.setOnAction(actionEvent -> {
            Optional<ButtonType> result =  AlertUtils.confirm("Are you sure you want to logout ?", "Logout").showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                chatSceneListener.onUserExit();
            }
        });
    }

    @Override
    protected void setupReturnButtonActions() {
        returnButton.setOnAction(actionEvent -> {
            chatSceneListener.onReturnButtonClicked();
        });
    }


    @Override
    protected void setBottomConstraints() {
        bottomBar.setPadding(new Insets(10));
        HBox.setHgrow(messageTextField, Priority.ALWAYS); // expand message input
        HBox.setHgrow(spacer, Priority.ALWAYS); // expand the space between return and logout buttons

        sendButton.setPrefWidth(100);
        attachButton.setTooltip(new Tooltip("Attach File"));
    }

    @Override
    protected void setTopConstraints() {
        logoutButton.setFocusTraversable(false);
        returnButton.setTooltip(new Tooltip("Return To Login Window"));

        topBar.setPadding(new Insets(5, 10, 5, 10));
        topBar.setAlignment(Pos.CENTER_RIGHT);
    }

    @Override
    protected void setCenterConstraints() {
        root.setPadding(new Insets(10));
        chatTextArea.setEditable(false);
        chatTextArea.setWrapText(true);
    }

    @Override
    protected void initControls() {
        chatTextArea = new TextArea();
        root = new BorderPane();
        spacer = new Region();
        attachButton = new Button();
        returnButton = new Button();
        messageTextField = new TextField();

        messageTextField.setPromptText("send your friends an insult to greet them");
        sendButton = new Button("Send");
        logoutButton = new Button("Logout");

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
    protected void setResourcesConstraints() {
        attachIcon.setFitHeight(ICON_HEIGHT);
        attachIcon.setFitWidth(ICON_WIDTH);

        returnIcon.setFitWidth(ICON_HEIGHT);
        returnIcon.setFitHeight(ICON_WIDTH);
    }
}
