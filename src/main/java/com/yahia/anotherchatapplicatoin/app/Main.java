package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new  Server(8080);
       chatServer.start();

       Client idiotClient1 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       Client idiotClient2 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       Client idiotClient3 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());

       idiotClient3.sendMessage("Hello from idiot client 1 to all idiots around");
    }
}
