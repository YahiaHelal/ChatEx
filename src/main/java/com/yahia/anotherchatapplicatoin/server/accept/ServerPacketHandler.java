package com.yahia.anotherchatapplicatoin.server.accept;

import java.io.IOException;

@FunctionalInterface
public interface ServerPacketHandler {
    void handle(ServerHandshakeContext ctx) throws IOException;
}
