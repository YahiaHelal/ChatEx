package com.yahia.chatio.protocol.packet;

//TODO: make generic <T>
//TODO: payload should be an object, or some type that is have to be one of provided messages
public record CommunicationPacket(
        PacketType type,
        String payload
) {}
