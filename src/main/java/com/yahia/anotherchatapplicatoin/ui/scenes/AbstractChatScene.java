package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ChatSceneListener;
import javafx.stage.Stage;

public abstract class AbstractChatScene {

    public final void init() {
        initControls();
        loadResources();
        applyConstraints();
        buildUi();
    }

    public abstract void wireController(ChatSceneListener chatListener, Stage stage);
    protected abstract void initControls();
    protected abstract void buildUi();
    protected abstract void setUpActions(Stage stage);
    protected abstract void applyConstraints();
    protected abstract void loadResources();
}
