package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.controllers.LoginSceneController;
import com.yahia.anotherchatapplicatoin.ui.controllers.listeners.LoginSceneListener;
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
    public int getPort() throws NumberFormatException{
        return Integer.parseInt(portTextField.getText());
    }
    public String getUsername() {
        return usernameTextField.getText();
    }
    public Scene getScene() {
        return loginScene;
    }


    @Override
    public void wireController(LoginSceneListener listener, Stage stage) {
        this.loginSceneListener = listener;
        setUpActions();
    }

    @Override
    protected void initControls() {
        usernameTextField = new TextField();
        ipAddressTextField = new TextField();
        portTextField = new TextField();
        loginButton = new Button("Login");
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
        LayoutUtils.setLoginGridSpacing(loginGrid);
        loginButton.setPrefWidth(75);
        usernameTextField.setPrefWidth(200);

    }

    @Override
    protected void setUpActions() {
        loginButton.setOnAction(actionEvent -> {
            loginSceneListener.onLoginButtonClicked(getUsername(), getIpAddress(), getPort());
        });
    }
}
