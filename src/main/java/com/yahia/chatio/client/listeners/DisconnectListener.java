package com.yahia.chatio.client.listeners;


import com.yahia.chatio.protocol.disconnect.DisconnectReason;

//NOTE: is an interface holding only one abstract method, like Runnable, more methods give compiler errors
//TODO: better naming, its only one event
@FunctionalInterface
public interface DisconnectListener {
    void onDisconnect(DisconnectReason reason);
}
