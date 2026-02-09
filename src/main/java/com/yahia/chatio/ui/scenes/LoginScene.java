package com.yahia.chatio.ui.scenes;

import com.yahia.chatio.ui.controls.PromptTextField;
import com.yahia.chatio.ui.scenes.base.AbstractLoginScene;
import com.yahia.chatio.ui.scenes.listeners.LoginSceneListener;
import com.yahia.chatio.utils.ui.LayoutUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LoginScene extends AbstractLoginScene {

    private PromptTextField usernameField;
    private PromptTextField serverField;

    private Button loginButton;
    private Button connectedServersButton;
    private Button launchServerButton;

    private GridPane loginGrid;
    private Scene loginScene;
    private final int WIDTH = 600, HEIGHT = 400;
    private LoginSceneListener loginSceneListener;

    public LoginScene() {
        init();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getServerName() {
        return serverField.getText();
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
        loginGrid = new GridPane();
        loginScene = new Scene(loginGrid, WIDTH, HEIGHT);

        usernameField = new PromptTextField("Username");
        serverField = new PromptTextField("Server Name");

        loginButton = new Button("Login");
        connectedServersButton = new Button("Connected Servers");
        launchServerButton = new Button("Launch A Server");
    }

    @Override
    protected void buildUi() {
        loginGrid.add(usernameField, 0, 0);
        loginGrid.add(serverField, 0, 1);
        loginGrid.add(loginButton, 0, 2);
        loginGrid.add(connectedServersButton, 0, 3);
        loginGrid.add(launchServerButton, 0, 4);
        LayoutUtils.setLoginGridSpacing(loginGrid);
    }

    @Override
    protected void applyConstraints() {
        usernameField.setPrefWidth(200);
        serverField.setPrefWidth(200);
        loginButton.setPrefWidth(200);
        connectedServersButton.setPrefWidth(200);
        launchServerButton.setPrefWidth(200);
    }

    @Override
    protected void setUpActions() {
        loginButton.setOnAction(actionEvent -> {
            if (loginSceneListener != null) {
                loginSceneListener.onLoginButtonClicked(getUsername(), getServerName());
            }
        });

        connectedServersButton.setOnAction(actionEvent -> {
            if (loginSceneListener != null) {
                loginSceneListener.onConnectedServersButtonClicked();
            }
        });

        launchServerButton.setOnAction(actionEvent -> {
            if (loginSceneListener != null) {
                loginSceneListener.onLaunchServerButtonClicked();
            }
        });
    }
}
