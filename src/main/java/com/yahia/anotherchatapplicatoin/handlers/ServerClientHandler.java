package com.yahia.anotherchatapplicatoin.handlers;

import com.google.gson.Gson;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.server.Server;

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

    //NOTE: only fired when a new client is authenticated by the HandShake
    public ServerClientHandler(Socket clientSocket, Server chatServer) throws IOException {
        this.CLIENT_SOCKET = clientSocket;
        this.CHAT_SERVER = chatServer;
        LOGGER = LogManager.getLogger();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMessageToClient(BroadCastMessage message) {
        out.println(prefixMessage(message.sender(), message.text()));
        LOGGER.log(Level.INFO, String.format("Message delivered to client %s successfully", CLIENT_SOCKET.getInetAddress().getHostAddress()));
    }

    private String prefixMessage(String clientName, String text) {
        return String.format("[%s]: %s", clientName, text);
    }
    private void handleBroadCast(CommunicationPacket packet){
        CHAT_SERVER.broadCastPacket(packet);
    };
    private void handlePrivateMessage(CommunicationPacket packet) {

    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
                CommunicationPacket clientPacket =  JsonHelper.GSON.fromJson(msg, CommunicationPacket.class);
                switch (clientPacket.type()) {
                    case BROADCAST_MESSAGE -> handleBroadCast(clientPacket);
                    case PRIVATE_MESSAGE -> handlePrivateMessage(clientPacket);
                }
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }finally {
            CHAT_SERVER.removeClient(this);
        }
    }


}
