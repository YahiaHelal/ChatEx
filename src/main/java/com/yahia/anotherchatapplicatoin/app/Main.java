package com.yahia.anotherchatapplicatoin.app;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //TODO: Auto-discovery of the server on LAN (clients won't need to type the Server's IP)
        //TODO: Auto-discovery: App broadcasts a UDP packet, Server replies with it's IP/PORT, clients automatically connects
        //TODO: Other option: DNS the Server IPs to make it more human friendly
        //TODO: The DNS service gives names to each newly connected Server
        //TODO: Each server may have up to machine threads number of chat rooms
        //TODO: List all available servers for each connected client to choose from
//        HandShakeRequest handShakeRequest = new HandShakeRequest("Yahia");
//        String usernameJson = JsonHelper.GSON.toJson(handShakeRequest);
//        CommunicationPacket sentPacket = new CommunicationPacket(MessageType.HANDSHAKE_REQUEST, usernameJson);
//        CommunicationPacket recPacket = JsonHelper.GSON.fromJson(JsonHelper.GSON.toJson(sentPacket), CommunicationPacket.class);
//        HandShakeRequest req = JsonHelper.GSON.fromJson(recPacket.payload(), HandShakeRequest.class);
//        System.out.println(req.username());
        Server chatServer = new Server(8082);
        chatServer.start();
    }
}
