package com.yahia.chatio.client.exceptions;

//TODO: handle user trying to send a message to a dead server
public class ClientDisconnectedException extends Exception {
    public ClientDisconnectedException(String message) {
        super(message);
    }
}
