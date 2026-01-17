package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.base.AbstractLoginScene;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.LoginSceneListener;
import com.yahia.anotherchatapplicatoin.utils.ui.LayoutUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginScene extends AbstractLoginScene {
    private TextField usernameTextField;
    private TextField ipAddressTextField;
    private TextField portTextField;
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

    public String getIpAddress() {
        return ipAddressTextField.getText();
    }
    public String getPort(){
        return portTextField.getText();
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
        ipAddressTextField = new TextField();
        portTextField = new TextField();
        loginButton = new Button("Login");
        connectedServersButton = new Button("Connected Servers");
        launchServerButton = new Button("Launch A Server");
        loginGrid = new GridPane();
        loginScene = new Scene(loginGrid, WIDTH, HEIGHT);
        usernameTextField.setPromptText("Username");
        ipAddressTextField.setPromptText("IP Address");
        portTextField.setPromptText("Port");

    }

    @Override
    protected void buildUi() {
        loginGrid.add(usernameTextField, 0, 0);
        loginGrid.add(ipAddressTextField, 0, 1);
        loginGrid.add(portTextField, 0, 2);
        loginGrid.add(loginButton, 0, 3);
        loginGrid.add(connectedServersButton, 0, 4);
        loginGrid.add(launchServerButton, 0, 5);
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
            loginSceneListener.onLoginButtonClicked(getUsername(), getIpAddress(), getPort());
        });

        connectedServersButton.setOnAction(actionEvent -> {
            loginSceneListener.onConnectedServersButtonClicked();
        });

        launchServerButton.setOnAction(actionEvent -> {
            loginSceneListener.onLaunchServerButtonClicked();
        });
    }
}
