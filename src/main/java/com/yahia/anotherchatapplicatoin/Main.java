package com.yahia.anotherchatapplicatoin;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new Server(8080);
       Client idiotClient = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       idiotClient.sendMessage("no iam the server, suck my wires");
       idiotClient.sendMessage("i used to be an adventurer like you then i took an arrow in the knee");
       idiotClient.sendMessage("must've been the wind");
    }
}
