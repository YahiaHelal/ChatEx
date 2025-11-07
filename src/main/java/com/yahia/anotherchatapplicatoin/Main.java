package com.yahia.anotherchatapplicatoin;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080);
        Client client = new Client(server.getServerAddress(), server.getServerPort());

    }
}
