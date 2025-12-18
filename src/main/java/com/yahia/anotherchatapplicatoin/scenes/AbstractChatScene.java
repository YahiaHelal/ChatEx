package com.yahia.anotherchatapplicatoin.scenes;

import com.yahia.anotherchatapplicatoin.client.Client;

public abstract class AbstractChatScene {

    public final void init(Client client) {
        initControls();
        buildUi();
        applyConstraints();
        initController(client);
        setUpActions();
    }

    protected abstract void initControls();
    protected abstract void buildUi();
    protected abstract void initController(Client client);
    protected abstract void setUpActions();
    protected abstract void applyConstraints();
}
