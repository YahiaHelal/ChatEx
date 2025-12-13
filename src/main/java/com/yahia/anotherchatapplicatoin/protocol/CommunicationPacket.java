package com.yahia.anotherchatapplicatoin.protocol;

public record CommunicationPacket(
        MessageType type,
        String payload
) {}
