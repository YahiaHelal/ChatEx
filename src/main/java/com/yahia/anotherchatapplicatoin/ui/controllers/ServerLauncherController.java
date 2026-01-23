package com.yahia.anotherchatapplicatoin.ui.controllers;


import com.yahia.anotherchatapplicatoin.ui.managers.network.ServersManager;
import com.yahia.anotherchatapplicatoin.ui.scenes.listeners.ServerSettingsListener;
import com.yahia.anotherchatapplicatoin.utils.alerts.AlertUtils;
import javafx.application.Platform;

import java.io.IOException;

public class ServerLauncherController implements ServerSettingsListener {


    @Override
    public void onLaunch(String name, String port) {
        Platform.runLater(() -> {
            try {
                if(ServersManager.runServer(name, Integer.parseInt(port))) {
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
        Platform.runLater(() -> {
            try {
                if(ServersManager.terminateServer(serverName)) {
                    AlertUtils.info("Server is Terminated Successfully", "Success").showAndWait();
                }else {
                    AlertUtils.warn("Server is not Running indeed", "Fail").showAndWait();
                }
            }catch (IOException e) {
                AlertUtils.error("Error occurred while closing server", "Fail").showAndWait();
            }
        });
    }
}
