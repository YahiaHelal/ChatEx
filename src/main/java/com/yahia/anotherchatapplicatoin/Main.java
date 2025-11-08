package com.yahia.anotherchatapplicatoin;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new Server();
       Client idiotClient = new Client(chatServer.getServerAddress(), chatServer.getServerPort());
       idiotClient.sendMessage("no iam the server, suck my wires");

    }
}
