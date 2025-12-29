package com.yahia.anotherchatapplicatoin.client.listeners;


//NOTE: is an interface holding only one abstract method, like Runnable, more methods give compiler errors
@FunctionalInterface
public interface ServerEventsListener {
    void onServerShutDown();
}
