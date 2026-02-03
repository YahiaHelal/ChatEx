package com.yahia.chatio.app;
import com.yahia.chatio.protocol.codec.payload.json.fin.JsonFinEncoder;
import com.yahia.chatio.protocol.terminate.FinPacket;

public class Main {
    public static void main(String[] args) {
        //TODO: Auto-discovery of the server on LAN (clients won't need to type the Server's IP)
        //TODO: Auto-discovery: App broadcasts a UDP packet, Server replies with it's IP/PORT, clients automatically connects
        //TODO: Other option: DNS the Server IPs to make it more human friendly
        //TODO: The DNS service gives names to each newly connected Server
        //TODO: Each server may have up to machine threads number of chat rooms
        //TODO: List all available servers for each connected client to choose from

        String x = new JsonFinEncoder().encode(new FinPacket(""));
//        String y = new JsonDisconnectRequestEncoder().encode(new DisconnectRequest("uahoa"));
//        Server chatServer = new Server(8081);
//        chatServer.start();
    }
}
