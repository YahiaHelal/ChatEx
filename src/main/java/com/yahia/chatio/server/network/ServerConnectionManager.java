package com.yahia.chatio.server.network;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.server.Server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.*;

public class ServerConnectionManager {
    private static final Map<ServerConnection, Server> runningServers = new ConcurrentHashMap<>();
    private static final Map<String, ServerConnection> serverInfo = new ConcurrentHashMap<>();
    private ServerConnectionManager() {}


    public static void addServer(ServerConnection connection, Server server) {
        serverInfo.put(connection.name(), connection);
        runningServers.put(connection,server);
    }

    public static Server getServer(ServerConnection connection) {
        return runningServers.get(connection);
    }

    public static boolean isServerRunning(ServerConnection connection) {
        return runningServers.containsKey(connection);
    }

    public static void removeServer(ServerConnection connection) {
        runningServers.remove(connection);
    }

    public static ServerConnection getServerConnection(String name) {
        return serverInfo.get(name);
    }

}
