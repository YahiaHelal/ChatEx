package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new Server(8080);
       Client idiotClient1 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       Client idiotClient2 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       Client idiotClient3 = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       idiotClient1.sendMessage("no iam the server, suck my wires");
       idiotClient2.sendMessage("i used to be an adventurer like you then i took an arrow in the knee");
       idiotClient3.sendMessage("must've been the wind");
    }
}
