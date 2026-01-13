package com.yahia.anotherchatapplicatoin.client;

import java.io.IOException;

public abstract class AbstractClient {

    public final void startNewClient(String serverIp, int port) throws IOException {
        connect(serverIp, port);
        initMessengers();
        registerHandlers();
//        registerEncoders();
//        registerDecoders();
        startListener();
    }

    protected abstract void connect(String serverIp, int port) throws IOException;
    protected abstract void initMessengers();
    protected abstract void startListener();
    protected abstract void registerHandlers();
}
