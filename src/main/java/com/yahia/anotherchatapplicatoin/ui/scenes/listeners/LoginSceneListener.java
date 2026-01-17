package com.yahia.anotherchatapplicatoin.ui.scenes.listeners;

public interface LoginSceneListener {
    void onLoginButtonClicked(String username, String ipAddress, String port);
    void onConnectedServersButtonClicked();
    void onLaunchServerButtonClicked();
}
