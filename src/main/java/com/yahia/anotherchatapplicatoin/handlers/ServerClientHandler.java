package com.yahia.anotherchatapplicatoin.handlers;

import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClientHandler implements Runnable {
    private final Logger LOGGER;
    private final Socket CLIENT_SOCKET;
    private final Server chatServer;

    public ServerClientHandler(Socket clientSocket, Server chatServer) {
        this.CLIENT_SOCKET = clientSocket;
        LOGGER = LogManager.getLogger();
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
                chatServer.broadCastMessage(msg);
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }
}
