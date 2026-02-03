package com.yahia.chatio.ui.scenes.base;

import com.yahia.chatio.ui.scenes.listeners.LoginSceneListener;

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
