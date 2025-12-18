package com.yahia.anotherchatapplicatoin.client;

public abstract class AbstractClient {

    public final void startNewClient(String serverIp, int port) {
        connect(serverIp, port);
        initMessengers();
        startListener();
    }

    protected abstract void connect(String serverIp, int port);
    protected abstract void initMessengers();
    protected abstract void startListener();
}
