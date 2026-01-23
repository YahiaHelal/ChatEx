package com.yahia.anotherchatapplicatoin.ui.managers.network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnectionManager {
    private static final Map<String, ArrayList<Socket>> activeConnections = new HashMap<>();
    private static final Map<String, String> serverNames = new HashMap<>(); // ip:port -> Custom name

    private ServerConnectionManager() {}


    public static void addConnection(String serverKey, Socket socket) {
        if(!activeConnections.containsKey(serverKey)) activeConnections.put(serverKey, new ArrayList<>());
        activeConnections.get(serverKey).add(socket);
    }


    public static boolean checkConnectionWithKey(String serverKey) {
        return activeConnections.containsKey(serverKey);
    }

    public static boolean serverNameTaken(String serverName) {
        return serverNames.containsValue(serverName);
    }



    public static void addServerName(String key, String name) {
        serverNames.put(key, name);
    }

}
