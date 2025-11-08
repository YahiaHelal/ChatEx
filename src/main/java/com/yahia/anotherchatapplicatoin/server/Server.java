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

    private final int SERVER_PORT = 8080;
    private final ArrayList<String> CLIENTS = new ArrayList<>();

    private ServerSocket serverSocket;
    private final Logger LOGGER = LogManager.getLogger();


    public Server() {
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



    // TODO: receive client insults in a new thread
    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    CLIENTS.add(clientSocket.getInetAddress().getHostAddress());
                    LOGGER.log(Level.FINE, String.format("Client %s connected to server %s:%d successfully", clientSocket.getInetAddress().getHostAddress(), getServerAddress(), SERVER_PORT));
                    new Thread(() -> receiveMessage(clientSocket)).start();
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


    // TODO: propagate client message through server, to all connected clients
    public void sendMessage(Socket clientSocket, String message) {
        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream());) {
            out.println(message);
            LOGGER.log(Level.INFO, "Server sent client message %s successfully", message);
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't send the message %s", message);
        }

    }

    public void receiveMessage(Socket clientSocket) {
      try {
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

          String msg;
          while((msg = in.readLine()) != null) {
              System.out.println(msg);
              LOGGER.log(Level.INFO, String.format("Server received a message: %s from %s", msg, clientSocket.getInetAddress().getHostAddress()));
          }
      }catch (IOException e) {
          LOGGER.log(Level.WARNING, "Server couldn't receive client message");
      }
    }

}
