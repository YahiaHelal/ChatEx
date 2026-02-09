package com.yahia.chatio.network.server;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.server.Server;

import javax.naming.InvalidNameException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConnectionManager {
    private static final Map<ServerConnection, Server> runningServers = new ConcurrentHashMap<>();
    private static final Map<String, ServerConnection> serverInfo = new ConcurrentHashMap<>();
    private ServerConnectionManager() {}


    public static void addServer(ServerConnection connection, Server server) {
        serverInfo.put(connection.name(), connection);
        runningServers.put(connection,server);
    }

    public static ServerConnection getServerConnection(String serverName) {
        return serverInfo.get(serverName);
    }

    public static Server getServer(String serverName) {
        return runningServers.get(serverInfo.get(serverName));
    }

    public static boolean isServerRunning(String serverName) {
        return serverInfo.containsKey(serverName);
    }

    public static void removeServer(String serverName) {
        runningServers.remove(serverInfo.get(serverName));
        serverInfo.remove(serverName);
    }

    public static void validateServerName(String serverName) throws InvalidNameException{
        if(serverName.isBlank()) {
            throw new InvalidNameException("Server Name cannot be empty");
        }
    }

}
