package com.yahia.chatio.network.server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPortAllocator implements PortAllocator{
    private static final Set<Integer> runningPorts = ConcurrentHashMap.newKeySet();
    private static final int startGen = 49152;
    private static final int endGen = 65535;

    @Override
    public int allocate() {
        int port;
        do {
             port = ThreadLocalRandom.current().nextInt(startGen, endGen + 1);
        }while(!runningPorts.add(port));
        return port;
    }

    @Override
    public void release(int port) {
        runningPorts.remove(port);
    }
}
