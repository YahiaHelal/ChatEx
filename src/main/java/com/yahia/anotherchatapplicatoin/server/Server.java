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

    private BufferedReader in;
    private PrintWriter out;

    public Server() {
        try {
            connect();
            listen();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Error creating server socket %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
        }
    }

    private void initMessengers(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            LOGGER.log(Level.CONFIG, "Messengers Initialized");
        }catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Error while initializing messengers");
        }

    }
    private void connect() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
    }



    // TODO: receive client insults in a new thread
    private void listen(){
        try {
            Socket clientSocket = serverSocket.accept();
            initMessengers(clientSocket);
            CLIENTS.add(clientSocket.getInetAddress().getHostAddress());
            LOGGER.log(Level.FINE, String.format("Client %s connected to server %s:%d successfully", clientSocket.getInetAddress().getHostAddress(), getServerAddress(), SERVER_PORT));
            new Thread(this::receiveMessage).start();
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't connect to client");
        }

    }


    public String getServerAddress() {
        return serverSocket.getInetAddress().getHostAddress();
    }
    public int getServerPort() {
        return SERVER_PORT;
    }


    // TODO: propagate client message through server, to all connected clients
    public void sendMessage(String message) {
        out.println(message);
    }

    public void receiveMessage() {
        try {
            String text = in.readLine();
            LOGGER.log(Level.INFO, String.format("Client sent a message %s", text));
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while collecting Client message");
        }
//        try {
//            while((msg = in.readLine()) != null) {
//                text = text.concat(msg);
//                break;
//            }
//        }catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error while collecting Client message");
//        }
//        return text;
    }

}
