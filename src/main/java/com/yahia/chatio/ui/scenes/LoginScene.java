package com.yahia.chatio.ui.scenes;

import com.yahia.chatio.ui.scenes.base.AbstractLoginScene;
import com.yahia.chatio.ui.scenes.listeners.LoginSceneListener;
import com.yahia.chatio.utils.ui.LayoutUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.InetSocketAddress;


public class LoginScene extends AbstractLoginScene {
    private TextField usernameTextField;
    private TextField serverName;
    private Button loginButton;
    private Button connectedServersButton;
    private Button launchServerButton;

    private GridPane loginGrid;
    private Scene loginScene;

    private final int WIDTH = 600, HEIGHT = 400;
    //TODO: may add list of listeners instead of one, more like a Pub/Sub Pattern
    //TODO: iterating through every listener and invoke the shared method
    private LoginSceneListener loginSceneListener;

    public LoginScene() {
        init();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }
    public Scene getScene() {
        return loginScene;
    }


    @Override
    public void wireController(LoginSceneListener listener) {
        this.loginSceneListener = listener;
        setUpActions();
    }

    @Override
    protected void initControls() {
        usernameTextField = new TextField();
        serverName = new TextField();

        loginButton = new Button("Login");
        connectedServersButton = new Button("Connected Servers"); //TODO: running servers
        launchServerButton = new Button("Launch A Server"); //TODO: server settings
        loginGrid = new GridPane();
        loginScene = new Scene(loginGrid, WIDTH, HEIGHT);
        usernameTextField.setPromptText("Username");
        serverName.setPromptText("Server name");

    }

    @Override
    protected void buildUi() {
        loginGrid.add(usernameTextField, 0, 0);
        loginGrid.add(serverName, 0, 1);;
        loginGrid.add(loginButton, 0, 2);
        loginGrid.add(connectedServersButton, 0, 3);
        loginGrid.add(launchServerButton, 0, 4);
        LayoutUtils.setLoginGridSpacing(loginGrid);
    }



    @Override
    protected void applyConstraints() {
        loginButton.setPrefWidth(200);
        connectedServersButton.setPrefWidth(200);
        launchServerButton.setPrefWidth(200);
        usernameTextField.setPrefWidth(200);
    }
    @Override
    protected void setUpActions() {
        loginButton.setOnAction(actionEvent -> {
            InetSocketAddress addr = loginSceneListener.getServerAddress(serverName.getText());
            loginSceneListener.onLoginButtonClicked(getUsername(), addr.getAddress().toString(), addr.getPort());
        });

        connectedServersButton.setOnAction(actionEvent -> {
            loginSceneListener.onConnectedServersButtonClicked();
        });

        launchServerButton.setOnAction(actionEvent -> {
            loginSceneListener.onLaunchServerButtonClicked();
        });
    }
}
