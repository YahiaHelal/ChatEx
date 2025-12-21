package com.yahia.anotherchatapplicatoin.ui.scenes;

import javafx.stage.Stage;

public abstract class AbstractLoginScene {

    public final void init(Stage stage) {
        initControls();
        buildUi();
        initController(stage);
        setUpActions();
    }

    protected abstract void initControls();
    protected abstract void buildUi();
    protected abstract void initController(Stage stage);
    protected abstract void setUpActions();

}
