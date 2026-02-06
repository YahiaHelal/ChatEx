package com.yahia.chatio.ui.scenes.listeners;

import java.net.InetSocketAddress;

public interface LoginSceneListener {
    void onLoginButtonClicked(String username, String ipAddress, int port);
    void onConnectedServersButtonClicked();
    void onLaunchServerButtonClicked();
    InetSocketAddress getServerAddress(String serverName);
}
