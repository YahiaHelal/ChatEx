package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new  Server(8080);
       chatServer.start();
    }
}
