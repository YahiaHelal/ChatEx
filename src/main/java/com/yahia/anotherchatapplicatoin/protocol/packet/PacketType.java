package com.yahia.anotherchatapplicatoin.protocol.packet;

public enum PacketType {
    HANDSHAKE_REQUEST,
    HANDSHAKE_RESPONSE,


    BROADCAST_MESSAGE,
    PRIVATE_MESSAGE,

    FIN,

    DISCONNECT_REQUEST,
}
