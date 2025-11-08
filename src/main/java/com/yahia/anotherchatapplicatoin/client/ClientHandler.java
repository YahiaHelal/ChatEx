package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Logger LOGGER = LogManager.getLogger();
    private final Socket CLIENT_SOCKET;
    private final ArrayList<String> CLIENTS;

    public ClientHandler(Socket clientSocket) {
        this.CLIENT_SOCKET = clientSocket;
        CLIENTS = new ArrayList<>();
    }

    // TODO: propagate client message to all clients
    private void sendMessage() {}
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            CLIENTS.add(CLIENT_SOCKET.getInetAddress().getHostAddress());
            String msg;
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, CLIENT_SOCKET.getInetAddress().getHostAddress()));
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }
}
