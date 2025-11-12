package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.managers.LogManager;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private final Logger LOGGER = LogManager.getLogger();
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;
    private String clientName;

    public Client(String serverIp, int serverPort, String clientName) throws IOException {
        this.clientName = clientName;
        connect(serverIp, serverPort);
        initMessengers();

    }

    //TODO: mark a word to end sending a message
    public void sendMessage(String message) {
        out.println(prefixMessage(message));
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    private void initMessengers(){
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            LOGGER.log(Level.INFO, "Messengers initialized");
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while initializing messengers");
        }

    }


    private void connect(String serverIp, int serverPort) throws IOException{
        clientSocket = new Socket(serverIp, serverPort);
    }

    private String prefixMessage(String message) {
        return String.format("[%s]: %s", clientName, message);
    }




    //TODO: receive messages in a new thread, multiple servers may try to write at the same time
    private void listen() {
        String text = "", msg;
        try {
            while((msg = in.readLine()) != null) {
                text = text.concat(msg);
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while collecting server message");
        }
    }
}
