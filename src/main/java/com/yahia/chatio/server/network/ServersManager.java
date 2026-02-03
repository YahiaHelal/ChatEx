package com.yahia.chatio.server.network;

import com.yahia.chatio.server.Server;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

//TODO: when a client tries to connect to a server name, we should query on central server containing every running server info
public class ServersManager {
    private static final ConcurrentHashMap<String, Server> runningServers = new ConcurrentHashMap<>();

    public static boolean runServer(String serverName, int port) {
        if(runningServers.containsKey(serverName)) return false;
        Server chatServer = new Server(port, serverName);
        chatServer.start();
        runningServers.put(serverName, chatServer);
        return true;
    }

    public static boolean terminateServer(String serverName) throws IOException {
        if(isServerRunning(serverName)) {
            runningServers.get(serverName).broadcastTermination();
            runningServers.get(serverName).terminate();
            runningServers.remove(serverName);
            return true;
        }
        return false;
    }

    public static boolean isServerRunning(String serverName) {
        return runningServers.containsKey(serverName);
    }

}
