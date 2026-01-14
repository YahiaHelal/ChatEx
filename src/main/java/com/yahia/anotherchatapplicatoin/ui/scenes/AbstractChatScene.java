package com.yahia.anotherchatapplicatoin.ui.scenes;

import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ChatSceneListener;
import javafx.stage.Stage;

public abstract class AbstractChatScene {

    public final void init() {
        initControls();
        loadResources();
        setConstraints();
        buildUi();
    }

    public abstract void wireController(ChatSceneListener chatListener, Stage stage);

    protected void setUpActions(Stage stage) {
        setupLoginButtonActions(stage);
        setupLogoutButtonActions();
        setupReturnButtonActions();
    }
    protected abstract void setupLoginButtonActions(Stage stage);
    protected abstract void setupLogoutButtonActions();
    protected abstract void setupReturnButtonActions();

    protected abstract void initControls();
    protected abstract void loadResources();
    protected abstract void buildUi();

    protected final void setConstraints() {
       setTopConstraints();
       setCenterConstraints();
       setBottomConstraints();
       setResourcesConstraints();
    }
    protected abstract void setBottomConstraints();
    protected abstract void setTopConstraints();
    protected abstract void setCenterConstraints();
    protected abstract void setResourcesConstraints();
}
