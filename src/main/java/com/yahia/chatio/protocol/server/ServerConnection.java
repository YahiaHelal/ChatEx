package com.yahia.chatio.protocol.server;

public record ServerConnection(String ip, String port) {
    public String id() {
        return String.format("%s:%s", ip, port);
    }
}
