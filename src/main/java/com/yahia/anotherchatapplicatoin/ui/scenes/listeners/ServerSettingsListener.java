package com.yahia.anotherchatapplicatoin.ui.scenes.listeners;

import com.yahia.anotherchatapplicatoin.server.Server;

import java.io.IOException;

public interface ServerSettingsListener {
    void onLaunch(String name, String port);
    void onTerminate(String serverName);
}
