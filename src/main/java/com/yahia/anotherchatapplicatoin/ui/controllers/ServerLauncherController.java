package com.yahia.anotherchatapplicatoin.ui.controllers;

import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ServerLauncherListener;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;

import java.io.IOException;

public class ServerLauncherController implements ServerLauncherListener {
    @Override
    public void onLaunchButtonClicked(String port) {
        try {
            Server chatServer = new Server(Integer.parseInt(port));
            chatServer.start();
            AlertUtils.info(String.format("Server is now available at %s:%d", chatServer.getIp(), chatServer.getPort()), "success").showAndWait();
        }catch (NumberFormatException e) {
            AlertUtils.error("Invalid Server Info", "Launch Failed").showAndWait();
        }catch (NullPointerException e) {
            AlertUtils.warn("Server is already running", "Launch Failed").showAndWait();
        }
    }
}
