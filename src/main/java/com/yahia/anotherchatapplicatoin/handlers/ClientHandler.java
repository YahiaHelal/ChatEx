package com.yahia.anotherchatapplicatoin.handlers;

import com.yahia.anotherchatapplicatoin.managers.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Logger LOGGER = LogManager.getLogger();
    private final Socket CLIENT_SOCKET;


    public ClientHandler(Socket clientSocket) {
        this.CLIENT_SOCKET = clientSocket;

    }

    // TODO: propagate client message to all clients
    private void sendMessage() {}
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }
}
