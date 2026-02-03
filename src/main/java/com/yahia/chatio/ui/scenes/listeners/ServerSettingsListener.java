package com.yahia.chatio.ui.scenes.listeners;

public interface ServerSettingsListener {
    void onLaunch(String name, String port);
    void onTerminate(String serverName);
}
