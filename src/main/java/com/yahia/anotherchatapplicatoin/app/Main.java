package com.yahia.anotherchatapplicatoin.app;

import com.yahia.anotherchatapplicatoin.client.Client;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
       Server chatServer = new  Server(8080);
       chatServer.start();

//       Client idiotClient1 = new Client("0.0.0.0", 8080, "Yahia");
//       Client idiotClient2 = new Client("0.0.0.0", 8080, "Omar");
//       Client idiotClient3 = new Client("0.0.0.0", 8080, "El mo7a abo tez magro7a");
//
//       idiotClient3.sendMessage("يتم احبنة");
    }
}
