package com.yahia.anotherchatapplicatoin;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Utils {
    public static void setGridSpacing(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(15);
    }

    public static void setVBoxSpacing(VBox vBox) {
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
    }

}
