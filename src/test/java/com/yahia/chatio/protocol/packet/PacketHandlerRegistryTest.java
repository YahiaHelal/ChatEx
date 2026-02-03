package com.yahia.chatio.protocol.packet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PacketHandlerRegistryTest {


    @Test
    void returnsRegisteredHandler() {
        PacketHandlerRegistry registry = new PacketHandlerRegistry();
        PacketHandler handler = packet -> {};
        registry.register(PacketType.BROADCAST_MESSAGE, handler);

        PacketHandler resolved = registry.get(PacketType.BROADCAST_MESSAGE);

        assertNotNull(resolved);
        assertSame(handler, resolved);
    }
}
