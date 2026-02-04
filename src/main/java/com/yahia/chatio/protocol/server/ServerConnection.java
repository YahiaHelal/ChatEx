package com.yahia.chatio.protocol.server;

public record ServerConnection(String ip, int port, String name) {
    public String id() {
        return String.format("[%s] %s:%d", name, ip, port);
    }
}
