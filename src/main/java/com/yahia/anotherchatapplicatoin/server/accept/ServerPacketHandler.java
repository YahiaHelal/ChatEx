package com.yahia.anotherchatapplicatoin.server.accept;

import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;

import java.io.IOException;

@FunctionalInterface
public interface ServerPacketHandler {
    void handle(ServerConnectionContext ctx) throws IOException;
}
