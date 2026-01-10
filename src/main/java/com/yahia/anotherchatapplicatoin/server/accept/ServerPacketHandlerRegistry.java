package com.yahia.anotherchatapplicatoin.server.accept;

import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;

import java.util.HashMap;

public class ServerPacketHandlerRegistry {
    private final HashMap<PacketType, ServerPacketHandler> serverHandlers = new HashMap<>();

    public ServerPacketHandlerRegistry(){}

    public void register(PacketType type, ServerPacketHandler handler) {
        serverHandlers.put(type, handler);
    }

    public ServerPacketHandler get(PacketType type) {
        return serverHandlers.get(type);
    }
}
