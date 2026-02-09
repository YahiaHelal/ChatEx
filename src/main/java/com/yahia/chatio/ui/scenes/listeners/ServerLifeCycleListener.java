package com.yahia.chatio.ui.scenes.listeners;

public interface ServerLifeCycleListener {
    void onLaunch(String serverName);
    void onTerminate(String serverName);
    void onReturnButtonClicked();
}
