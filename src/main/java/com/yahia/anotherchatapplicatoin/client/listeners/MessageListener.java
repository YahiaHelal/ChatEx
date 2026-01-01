package com.yahia.anotherchatapplicatoin.client.listeners;

@FunctionalInterface
public interface MessageListener {
    void onMessage(String msg);
}
