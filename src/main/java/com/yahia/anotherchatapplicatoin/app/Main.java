package com.yahia.anotherchatapplicatoin.app;
import com.yahia.anotherchatapplicatoin.server.Server;

public class Main {
    public static void main(String[] args) {
        //TODO: Auto-discovery of the server on LAN (clients won't need to type the Server's IP)
        //TODO: Auto-discovery: App broadcasts a UDP packet, Server replies with it's IP/PORT, clients automatically connects
        //TODO: Other option: DNS the Server IPs to make it more human friendly
        Server chatServer = new Server(8080);
        chatServer.start();
    }
}
