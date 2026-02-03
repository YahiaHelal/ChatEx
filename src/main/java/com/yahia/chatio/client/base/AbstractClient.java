package com.yahia.chatio.client.base;

import java.io.IOException;

public abstract class AbstractClient {

    public final void startNewClient(String serverIp, int port) throws IOException {
        connect(serverIp, port);
        initMessengers();
        registerHandlers();
        startListener();
    }

    protected abstract void connect(String serverIp, int port) throws IOException;
    protected abstract void initMessengers();
    protected abstract void startListener();
    protected abstract void registerHandlers();
}
