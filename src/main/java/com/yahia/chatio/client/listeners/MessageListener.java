package com.yahia.chatio.client.listeners;

@FunctionalInterface
public interface MessageListener {
    void onMessage(String msg);
}
