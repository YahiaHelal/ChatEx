package com.yahia.chatio.network.server;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.server.Server;

import java.io.IOException;

//TODO: when a client tries to connect to a server name, we should query on central server containing every running server info
public class ServersManager {


    public static boolean runServer(ServerConnection connection) {
        if(ServerConnectionManager.isServerRunning(connection)) return false;
        Server chatServer = new Server(connection);
        chatServer.start();
        ServerConnectionManager.addServer(connection, chatServer);
        return true;
    }

    public static boolean terminateServer(ServerConnection connection) throws IOException {
        if(ServerConnectionManager.isServerRunning(connection)) {
            var server = ServerConnectionManager.getServer(connection);
            server.broadcastTermination();
            server.terminate();
            ServerConnectionManager.removeServer(connection);
            return true;
        }
        return false;
    }

}
