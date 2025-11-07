package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private int serverPort;

    private ServerSocket serverSocket;
    private final Logger LOGGER = LogManager.getLogger();
    private final ArrayList<String> CLIENTS = new ArrayList<>();


    public Server(int serverPort) {
        this.serverPort = serverPort;
        try {
            connect();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Error creating server socket %s:%d", serverSocket.getInetAddress().getHostAddress(), this.serverPort));
        }
    }
    private void connect() throws IOException {
        serverSocket = new ServerSocket(this.serverPort);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), serverPort));
    }



    // TODO: receive client insults in a new thread
    private void listen() throws IOException{
        while(true) {
            Socket clientSocket = serverSocket.accept();
            CLIENTS.add(clientSocket.getInetAddress().getHostAddress());
            LOGGER.log(Level.INFO, String.format("Client %s connected to server %s:%d successfully", clientSocket.getInetAddress().getHostAddress(), getServerAddress(), serverPort));

        }
    }


    public String getServerAddress() {
        return serverSocket.getInetAddress().getHostAddress();
    }
    public int getServerPort() {
        return serverPort;
    }


    // TODO: propagate client message through server, to all connected clients
    public void sendMessage(String message) {}

}
