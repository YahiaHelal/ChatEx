package com.yahia.anotherchatapplicatoin.handlers;

import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.utils.network.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClientHandler implements Runnable {
    private final Logger LOGGER;
    private final Socket CLIENT_SOCKET;
    private final Server CHAT_SERVER; // TODO: client can move from one server to another
    private final PrintWriter out;
    private String clientUsername;

    //NOTE: only fired when a new client is authenticated by the HandShake
    public ServerClientHandler(Socket clientSocket, Server chatServer, String clientUsername) throws IOException {
        this.CLIENT_SOCKET = clientSocket;
        this.CHAT_SERVER = chatServer;
        LOGGER = LogManager.getLogger();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.clientUsername = clientUsername;
    }



    public void sendMessageToClient(CommunicationPacket packet) {
        CommunicationPacket broadBastPacket = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, packet.payload());
        out.println(JsonHelper.GSON.toJson(broadBastPacket));
        LOGGER.log(Level.INFO, String.format("Message delivered to client %s successfully", SocketUtils.getSocketAddress(CLIENT_SOCKET)));
    }


    private void updateClientUsername(String newName) {
        this.clientUsername = newName;
    }
    private void handleBroadCast(CommunicationPacket packet){
        CHAT_SERVER.broadCastPacket(packet);
    };

    //TODO: private messages between friends only
    private void handlePrivateMessage(CommunicationPacket packet) {

    }
    private void handleDisconnection(CommunicationPacket packet) {
        DisconnectRequest request = JsonHelper.GSON.fromJson(packet.payload(), DisconnectRequest.class);
        CHAT_SERVER.removeClient(this, request.username());
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
                CommunicationPacket clientPacket =  JsonHelper.GSON.fromJson(msg, CommunicationPacket.class);
                //TODO: Include system messages, like when a client changes it's username
                switch (clientPacket.type()) {
                    case BROADCAST_MESSAGE -> handleBroadCast(clientPacket);
                    case PRIVATE_MESSAGE -> handlePrivateMessage(clientPacket);
                    case DISCONNECT_REQUEST -> handleDisconnection(clientPacket);
                }
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }


}
