package com.yahia.chatio.server.network;

public interface PortAllocator {
    int allocate();
    void release(int port);
}
