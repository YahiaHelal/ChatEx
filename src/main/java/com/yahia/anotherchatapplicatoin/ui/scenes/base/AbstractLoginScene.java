package com.yahia.anotherchatapplicatoin.ui.scenes.base;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.LoginSceneListener;
import javafx.stage.Stage;

public abstract class AbstractLoginScene {

    public final void init() {
        initControls();
        applyConstraints();
        buildUi();
    }

    public abstract void wireController(LoginSceneListener listener);
    protected abstract void initControls();
    protected abstract void buildUi();
    protected abstract void setUpActions();
    protected abstract void applyConstraints();
}
