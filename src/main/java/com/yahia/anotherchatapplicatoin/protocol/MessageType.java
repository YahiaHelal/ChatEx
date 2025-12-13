package com.yahia.anotherchatapplicatoin.protocol;

public enum MessageType {
    HANDSHAKE_REQUEST,
    HANDSHAKE_RESPONSE,


    BROADCAST_MESSAGE,
    PRIVATE_MESSAGE,
    SERVER_GREETINGS,

    SERVER_STATUS,

    DISCONNECT_REQUEST,
    DISCONNECT_RESPONSE
}
