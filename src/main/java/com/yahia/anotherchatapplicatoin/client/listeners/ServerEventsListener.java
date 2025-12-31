package com.yahia.anotherchatapplicatoin.client.listeners;


import com.yahia.anotherchatapplicatoin.protocol.DisconnectReason;

//NOTE: is an interface holding only one abstract method, like Runnable, more methods give compiler errors
//TODO: better naming, its only one event
@FunctionalInterface
public interface ServerEventsListener {
    void onDisconnect(DisconnectReason reason);
}
