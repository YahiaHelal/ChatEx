package com.yahia.chatio.protocol.packet;

public enum PacketType {
    HANDSHAKE_REQUEST,
    HANDSHAKE_RESPONSE,


    BROADCAST_MESSAGE,
    PRIVATE_MESSAGE,

    NEW_SERVER,

    FIN,

    DISCONNECT_REQUEST,
}
