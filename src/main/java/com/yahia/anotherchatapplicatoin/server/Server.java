package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.LogManager;
import com.yahia.anotherchatapplicatoin.client.ClientHandler;

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

    private final int SERVER_PORT;

    private ServerSocket serverSocket;
    private final Logger LOGGER = LogManager.getLogger();


    public Server(int serverPort) {
        this.SERVER_PORT = serverPort;
        try {
            connect();
            listen();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Error creating server socket %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
        }
    }

    private void connect() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
    }



    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOGGER.log(Level.FINE, String.format("Client %s connected to server %s:%d successfully", clientSocket.getInetAddress().getHostAddress(), getServerAddress(), SERVER_PORT));
                    new Thread(() -> new ClientHandler(clientSocket)).start();
                }catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Server couldn't connect to client");
                }
            }
        }).start();
    }


    public String getServerAddress() {
        return serverSocket.getInetAddress().getHostAddress();
    }
    public int getServerPort() {
        return SERVER_PORT;
    }


    // TODO: re-write in client handler
    public void sendMessage(Socket clientSocket, String message) {
        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream());) {
            out.println(message);
            LOGGER.log(Level.INFO, "Server sent client message %s successfully", message);
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't send the message %s", message);
        }

    }

}
