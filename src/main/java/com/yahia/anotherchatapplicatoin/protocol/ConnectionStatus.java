package com.yahia.anotherchatapplicatoin.protocol;

public enum ConnectionStatus {
    ACCEPT("Welcome to Chat Room!"),
    REJECT_USERNAME_TAKEN("Username already taken"),
    REJECT_INVALID_USERNAME("Invalid username"),
    REJECT_SERVER_FULL("Server is full"),
    REJECT_IO("Server unreachable"),
    UNKNOWN("Unknown error");

    private final String msg;

    ConnectionStatus(String msg) {
        this.msg = msg;
    }

    public String message() {
        return msg;
    }
}
