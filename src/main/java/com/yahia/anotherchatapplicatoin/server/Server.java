package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.LogManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static final int SERVER_PORT = 8080;

    private final Logger logger = LogManager.getLogger();

    public Server() {
        listen();
    }
    private void listen() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            logger.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
        }catch (IOException e) {
            logger.log(Level.SEVERE, "Error in creating server socket");
        }
    }
}
