package com.yahia.chatio.ui.controllers;


import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.server.network.PortAllocator;
import com.yahia.chatio.server.network.RandomPortAllocator;
import com.yahia.chatio.server.network.ServerConnectionManager;
import com.yahia.chatio.server.network.ServersManager;
import com.yahia.chatio.ui.scenes.listeners.ServerLifeCycleListener;

import com.yahia.chatio.utils.alerts.AlertUtils;
import javafx.application.Platform;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

//TODO: Create Central Server Containing all Running Servers by far
//TODO: a map of [device's name] -> list of ServerConnectionManagers
public class ServerLifeCycleController implements ServerLifeCycleListener {
    private static final PortAllocator randPortAllocator = new RandomPortAllocator();

    @Override
    public void onLaunch(String serverName) {
        //BUG: get server ip from the central server saving name -> info
        ServerConnection connection = new ServerConnection("localhost",randPortAllocator.allocate(), serverName);
        Platform.runLater(() -> {
            try {
                if(ServersManager.runServer(connection)) {
                    AlertUtils.info("Server is now available", "success").showAndWait();
                }else {
                    AlertUtils.warn("Server is already running", "Launch Failed").showAndWait();
                }
            }catch (NumberFormatException e) {
                AlertUtils.error("Invalid Server Info", "Launch Failed").showAndWait();
            }
        });
    }

    @Override
    public void onTerminate(String serverName) {
        ServerConnection connection = ServerConnectionManager.getServerConnection(serverName);
        Platform.runLater(() -> {
            try {
                if(ServersManager.terminateServer(connection)) {
                    AlertUtils.info("Server is Terminated Successfully", "Success").showAndWait();
                    randPortAllocator.release(connection.port());
                }else {
                    AlertUtils.warn("Server is not Running indeed", "Fail").showAndWait();
                }
            }catch (IOException e) {
                AlertUtils.error("Error occurred while closing server", "Fail").showAndWait();
            }
        });
    }


}
