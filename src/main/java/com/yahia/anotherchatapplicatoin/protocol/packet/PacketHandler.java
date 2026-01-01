package com.yahia.anotherchatapplicatoin.protocol.packet;

@FunctionalInterface
public interface PacketHandler {
    void handlePacket(CommunicationPacket packet);
}
