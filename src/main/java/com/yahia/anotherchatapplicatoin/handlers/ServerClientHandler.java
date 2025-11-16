package com.yahia.anotherchatapplicatoin.handlers;

import com.yahia.anotherchatapplicatoin.managers.LogManager;
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

    //TODO: server fetches old messages from a NoSql db, somehow
    public ServerClientHandler(Socket clientSocket, Server chatServer) throws IOException {
        this.CLIENT_SOCKET = clientSocket;
        this.CHAT_SERVER = chatServer;
        LOGGER = LogManager.getLogger();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void sendMessage(String msg) {
        out.println(msg);
        LOGGER.log(Level.INFO, String.format("Message delivered to client %s successfully", CLIENT_SOCKET.getInetAddress().getHostAddress()));
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
                CHAT_SERVER.broadCastMessage(msg);
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }
}
