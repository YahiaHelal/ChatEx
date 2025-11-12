package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new  Server(8080);
       chatServer.start();

       Client idiotClient1 = new Client("0.0.0.0", 8080);
       Client idiotClient2 = new Client("0.0.0.0", 8080);
       Client idiotClient3 = new Client("0.0.0.0", 8080);

       idiotClient1.sendMessage("Hello from idiot client 2 to all idiots around");
    }
}
