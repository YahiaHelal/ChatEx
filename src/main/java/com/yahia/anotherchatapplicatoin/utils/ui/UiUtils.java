package com.yahia.anotherchatapplicatoin.utils.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

public class UiUtils {
    public static void setLoginGridSpacing(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(15);

    }

    public static Alert createAlert(Alert.AlertType type, String message, String header) {
        Alert alert = new Alert(type, message);
        alert.setTitle(type.toString());
        alert.setHeaderText(header);
        return alert;
    }

}
