package com.yahia.chatio.protocol.packet;

import java.util.HashMap;
import java.util.Map;

public class PacketHandlerRegistry {
    private final Map<PacketType, PacketHandler> handlers = new HashMap<>();

    public PacketHandlerRegistry() {}

    public void register(PacketType type, PacketHandler handler) {
        handlers.put(type, handler);
    }

    public PacketHandler get(PacketType type) {
        return handlers.get(type);
    }
}
