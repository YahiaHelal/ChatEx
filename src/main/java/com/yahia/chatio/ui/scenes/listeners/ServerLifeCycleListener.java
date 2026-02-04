package com.yahia.chatio.ui.scenes.listeners;

import com.yahia.chatio.protocol.server.ServerConnection;

public interface ServerLifeCycleListener {
    void onLaunch(String serverName);
    void onTerminate(String serverName);
}
