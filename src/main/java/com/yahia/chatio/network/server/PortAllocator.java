package com.yahia.chatio.network.server;

public interface PortAllocator {
    int allocate();
    void release(int port);
}
