package com.yahia.chatio.utils.alerts;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static Alert error(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(Alert.AlertType.ERROR.toString());
        alert.setHeaderText(header);
        return alert;
    }

    public static Alert confirm(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle(Alert.AlertType.CONFIRMATION.toString());
        alert.setHeaderText(header);
        return alert;
    }
    public static Alert warn(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setTitle(Alert.AlertType.WARNING.toString());
        alert.setHeaderText(header);
        return alert;
    }

    public static Alert info(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setTitle(Alert.AlertType.INFORMATION.toString());
        alert.setHeaderText(header);
        return alert;
    }
    public static Alert none(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.NONE, message);
        alert.setTitle(Alert.AlertType.NONE.toString());
        alert.setHeaderText(header);
        return alert;
    }

    private AlertUtils() {}
}
