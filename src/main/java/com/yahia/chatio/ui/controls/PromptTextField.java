package com.yahia.chatio.ui.controls;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * A TextField with a prompt label that stays visible until typing.
 * Can replace normal TextField in your UI scenes.
 */
public class PromptTextField extends StackPane {

    private final TextField textField;
    private final Label promptLabel;

    public PromptTextField(String promptText) {
        this.textField = new TextField();
        this.promptLabel = new Label(promptText);

        promptLabel.setStyle("-fx-text-fill: gray; -fx-padding: 3 0 0 5;");
        promptLabel.setMouseTransparent(true); // clicks pass to TextField

        getChildren().addAll(textField, promptLabel);

        textField.textProperty().addListener((obs, oldText, newText) -> {
            promptLabel.setVisible(newText.isEmpty());
        });
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    public TextField getTextField() {
        return textField;
    }
}
