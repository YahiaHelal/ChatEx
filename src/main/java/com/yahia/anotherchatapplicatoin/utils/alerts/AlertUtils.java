package com.yahia.anotherchatapplicatoin.utils.alerts;

import javafx.scene.control.Alert;

public class AlertUtils {
    public static Alert createAlert(Alert.AlertType type, String message, String header) {
        Alert alert = new Alert(type, message);
        alert.setTitle(type.toString());
        alert.setHeaderText(header);
        return alert;
    }
    private AlertUtils() {}
}
