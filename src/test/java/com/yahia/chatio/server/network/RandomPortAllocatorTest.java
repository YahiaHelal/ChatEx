package com.yahia.chatio.server.network;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomPortAllocatorTest {

    @Test
    void allocateReleaseTest() {
        PortAllocator portAllocator = new RandomPortAllocator();

        int port1 = portAllocator.allocate();
        int port2 = portAllocator.allocate();

        assertTrue(port1 >= 49152  && port1 <= 65535);
        assertTrue(port2 >= 49152 && port2 <= 65535);

        assertNotEquals(port1, port2);

        portAllocator.release(port1);
        int port3 = portAllocator.allocate(); // release and reuse

        assertTrue(port3 >= 49152  && port3 <= 65535);
    }
}
