package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.LoginSceneListener;
import javafx.stage.Stage;

public abstract class AbstractLoginScene {

    public final void init() {
        initControls();
        buildUi();
    }

    public abstract void wireController(LoginSceneListener listener ,Stage stage);
    protected abstract void initControls();
    protected abstract void buildUi();
    protected abstract void setUpActions();

}
