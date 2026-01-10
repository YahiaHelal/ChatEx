package com.yahia.anotherchatapplicatoin.protocol.packet;

import java.io.IOException;

@FunctionalInterface
public interface PacketHandler {
    void handlePacket(CommunicationPacket packet) throws IOException;
}
