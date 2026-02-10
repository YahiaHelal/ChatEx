package com.yahia.chatio.network.server;

import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.server.Server;

import javax.naming.InvalidNameException;
import java.io.IOException;

//TODO: when a client tries to connect to a server name, we should query on central server containing every running server info
public class ServersManager {


    public static boolean runServer(ServerConnection connection) throws InvalidNameException {

        ServerConnectionManager.validateServerName(connection.name());
        if(ServerConnectionManager.isServerRunning(connection.name())) return false;
        Server chatServer = new Server(connection);
        chatServer.start();
        ServerConnectionManager.addServer(connection, chatServer);
        return true;
    }

    public static boolean terminateServer(String serverName) throws InvalidNameException, IOException {
        ServerConnectionManager.validateServerName(serverName);
        if(ServerConnectionManager.isServerRunning(serverName)) {
            var server = ServerConnectionManager.getServer(serverName);
            server.broadcastTermination();
            server.terminate();
            ServerConnectionManager.removeServer(serverName);
            return true;
        }
        return false;
    }

}
