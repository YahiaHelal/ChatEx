package com.yahia.anotherchatapplicatoin.client.exceptions;

//TODO: handle user trying to send a message to a dead server
public class ClientDisconnectedException extends RuntimeException {
    public ClientDisconnectedException(String message) {
        super(message);
    }
}
