package com.yahia.chatio.ui.controllers;


import com.yahia.chatio.network.mdns.MdnsAnnouncer;
import com.yahia.chatio.network.mdns.MdnsDiscovery;
import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.network.server.PortAllocator;
import com.yahia.chatio.network.server.RandomPortAllocator;
import com.yahia.chatio.network.server.ServerConnectionManager;
import com.yahia.chatio.network.server.ServersManager;
import com.yahia.chatio.ui.managers.SceneNavigator;
import com.yahia.chatio.ui.scenes.listeners.ServerLifeCycleListener;

import com.yahia.chatio.utils.alerts.AlertUtils;
import com.yahia.chatio.utils.logging.LogManager;
import javafx.application.Platform;

import javax.jmdns.ServiceInfo;
import javax.naming.InvalidNameException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLifeCycleController implements ServerLifeCycleListener {
    private static final PortAllocator randPortAllocator = new RandomPortAllocator();
    private SceneNavigator navigator;
    private MdnsAnnouncer announcer;
    private final MdnsDiscovery discovery;
    private static final Logger LOGGER = LogManager.getLogger();


    public ServerLifeCycleController(SceneNavigator navigator, MdnsDiscovery discovery) {
        this.discovery = discovery;
        this.navigator = navigator;
        try {
            announcer = MdnsAnnouncer.getInstance();
        } catch (IOException e) {
            AlertUtils.warn("Can't fire multiple mdns on the same address", "Network Error").showAndWait();
            navigator.showLoginScene();
        }
    }

    @Override
    public void onLaunch(String serverName) {
        int port = randPortAllocator.allocate();
        ServerConnection connection = new ServerConnection(serverName, port);
        Platform.runLater(() -> {
            try {
                if(ServersManager.runServer(connection)) {
                    AlertUtils.info("Server is now available", "success").showAndWait();
                    announcer.announce(connection);
                    LOGGER.log(Level.INFO, "Announcement Success");
                }else {
                    AlertUtils.warn("Server is already running", "Launch Failed").showAndWait();
                }
            }catch (InvalidNameException e) {
                AlertUtils.error("Invalid Server Info", "Launch Failed").showAndWait();
            }catch (IOException ex) {
                AlertUtils.error("Unidentified host", "Network error").showAndWait();
                LOGGER.log(Level.SEVERE, String.format("Network error has occurred: %s", ex.getMessage()));
            }
        });
    }

    @Override
    public void onTerminate(String serverName) {
        InetSocketAddress addr = discovery.getServerAddress(serverName);
        Platform.runLater(() -> {
            try {
                LOGGER.log(Level.INFO, String.format("%s requested to be terminated", addr.getAddress()));
                if(ServersManager.terminateServer(serverName)) {
                    AlertUtils.info("Server Terminated Successfully", "Success").showAndWait();
                    randPortAllocator.release(addr.getPort());
                    announcer.stopService(serverName);
                }else {
                    AlertUtils.warn("Server is not Running indeed", "Termination Failed").showAndWait();
                }
            }catch (IOException e) {
                AlertUtils.error("Error occurred while closing server", "Termination Failed").showAndWait();
            }catch (InvalidNameException ex) {
                AlertUtils.error("Invalid Server Info", "Termination Failed").showAndWait();
            }
        });
    }

    @Override
    public void onReturnButtonClicked() {
        navigator.showLoginScene();
    }


}
