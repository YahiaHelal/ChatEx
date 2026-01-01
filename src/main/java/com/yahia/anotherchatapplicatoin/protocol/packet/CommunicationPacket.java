package com.yahia.anotherchatapplicatoin.protocol.packet;

//TODO: make generic <T>
public record CommunicationPacket(
        PacketType type,
        String payload
) {}
