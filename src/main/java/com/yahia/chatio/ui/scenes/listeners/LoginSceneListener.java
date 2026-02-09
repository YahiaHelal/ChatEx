package com.yahia.chatio.ui.scenes.listeners;

import java.net.InetSocketAddress;

public interface LoginSceneListener {
    void onLoginButtonClicked(String username, String serverName);
    void onConnectedServersButtonClicked();
    void onLaunchServerButtonClicked();
}
