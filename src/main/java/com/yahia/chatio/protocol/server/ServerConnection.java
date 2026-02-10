package com.yahia.chatio.protocol.server;

public record ServerConnection(String name, int port) {
    public String id() {
        return String.format("[%s] :%d", name, port);
    }
}
