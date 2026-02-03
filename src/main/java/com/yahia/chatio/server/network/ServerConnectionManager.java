package com.yahia.chatio.server.network;

import com.yahia.chatio.protocol.server.ServerConnection;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnectionManager {
    private static final Map<String, ArrayList<Socket>> activeConnections = new HashMap<>();
    private static final Map<String, ServerConnection> runningServersInfo = new HashMap<>();

    private ServerConnectionManager() {}


    public static boolean addServerInfo(String serverName, ServerConnection connection) {
        if(runningServersInfo.containsKey(serverName)) return false; // trying to run server with same name
        runningServersInfo.put(serverName, connection);
        return true;
    }

    public static void addConnection(String serverKey, Socket socket) {
        if(!activeConnections.containsKey(serverKey)) activeConnections.put(serverKey, new ArrayList<>());
        activeConnections.get(serverKey).add(socket);
    }


}
