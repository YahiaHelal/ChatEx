package com.yahia.chatio.ui.controllers;


import com.yahia.chatio.network.mdns.MdnsAnnouncer;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.network.server.PortAllocator;
import com.yahia.chatio.network.server.RandomPortAllocator;
import com.yahia.chatio.network.server.ServerConnectionManager;
import com.yahia.chatio.network.server.ServersManager;
import com.yahia.chatio.ui.scenes.listeners.ServerLifeCycleListener;

import com.yahia.chatio.utils.alerts.AlertUtils;
import com.yahia.chatio.utils.logging.LogManager;
import javafx.application.Platform;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO: Create Central Server Containing all Running Servers by far
//TODO: a map of [device's name] -> list of ServerConnectionManagers
//TODO: replace ip in the ServerConnection with the device's name
//TODO: a DeviceInfo class that gets the host device name that we will map with
public class ServerLifeCycleController implements ServerLifeCycleListener {
    private static final PortAllocator randPortAllocator = new RandomPortAllocator();

    private static final MdnsAnnouncer announcer = new MdnsAnnouncer();
    private final MdnsDiscovery discovery;

    public ServerLifeCycleController(MdnsDiscovery discovery) {
        this.discovery = discovery;
    }

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onLaunch(String serverName) {

        int port = randPortAllocator.allocate();
        ServerConnection connection = new ServerConnection("localhost", port, serverName);
        Platform.runLater(() -> {
            try {
                if(ServersManager.runServer(connection)) {
                    AlertUtils.info("Server is now available", "success").showAndWait();
                    try {
                        announcer.announce(serverName, port);
                    }catch (Exception e) {
                        AlertUtils.error("Unidentified host", "Network error").showAndWait();
                        LOGGER.log(Level.SEVERE, String.format("Network error has occurred: %s", e.getMessage()));
                    }
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
        InetSocketAddress addr = discovery.getServerAddress(serverName);
        ServerConnection connection = new ServerConnection(addr.getAddress().toString(), addr.getPort(), serverName);
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
