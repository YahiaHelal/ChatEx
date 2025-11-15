package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.utils.ui.UiUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoginScene{
    private TextField usernameTextField;
    private TextField ipAddressTextField;
    private TextField portTextField;
    private Button loginButton;
    private GridPane loginGrid;
    private Scene loginScene;
    private final int WIDTH = 600, HEIGHT = 400;

    private void initControls() {
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

    private void buildUi() {
        loginGrid.add(usernameTextField, 0, 0);
        loginGrid.add(ipAddressTextField, 0, 1);
        loginGrid.add(portTextField, 0, 2);
        loginGrid.add(loginButton, 0, 3);
        UiUtils.setLoginGridSpacing(loginGrid);
        loginButton.setPrefWidth(75);
        usernameTextField.setPrefWidth(200);

    }
    private void addActions() {
        loginButton.setOnAction(actionEvent -> {
            int serverPort = Integer.parseInt(portTextField.getText());
            String serverIp = ipAddressTextField.getText(); //TODO: validate ip with regex
            String username = usernameTextField.getText();

            try {
                new Client(serverIp, serverPort, username);
            }catch (IOException e) {
                UiUtils.createAlert(Alert.AlertType.ERROR, "no server with that ip is currently running", "Failed to connect to server").showAndWait();
            }
        });
    }

    public LoginScene() {
        initControls();
        addActions();
        buildUi();
    }

    public Scene getScene() {
        return loginScene;
    }
    public Button getLoginButton() {
        return loginButton;
    }
}
