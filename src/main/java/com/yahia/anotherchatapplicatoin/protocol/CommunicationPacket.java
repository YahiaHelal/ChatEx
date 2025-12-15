package com.yahia.anotherchatapplicatoin.protocol;

//TODO: make generic <T>
public record CommunicationPacket(
        MessageType type,
        String payload
) {}
