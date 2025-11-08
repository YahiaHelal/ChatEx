package com.yahia.anotherchatapplicatoin.utils;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LayoutUtils {
    public static void setLoginGridSpacing(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(15);
//        gridPane.setPadding(new Insets(10));

    }

}
