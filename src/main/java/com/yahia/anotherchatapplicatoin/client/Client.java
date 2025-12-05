package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.client.lisitener.MessageListener;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.ConnectionStatus;

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
    private MessageListener listener;

    public void setMessageListener(MessageListener listener) {
        this.listener = listener;
    }

    //TODO: client fetches the server's old messages when connected
    public Client(String clientName) {
        this.clientName = clientName;
    }

    public static ConnectionStatus handShake(String serverIp, int port, String username) {
        try(Socket temp = new Socket(serverIp, port)) {
            PrintWriter out = new PrintWriter(temp.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(temp.getInputStream()));

            out.println(username);
            String response = in.readLine();
            return ConnectionStatus.valueOf(response);

        }catch (Exception e) {
            return ConnectionStatus.REJECT_IO;
        }
    }

    public void connectAndStart(String serverIp, int serverPort) {
        connect(serverIp, serverPort);
        initMessengers();
        sendUsername(clientName);
        startListener();
    }

    //TODO: server-client protocol: client name first, then messages follow
    public void sendMessage(String message, boolean firstConnection) {
        if(firstConnection) {
            out.println(prefixMessage("SERVER", message));
        }else {
            out.println(prefixMessage(clientName, message));
        }
    }

    public void sendUsername(String name) {
        out.println(name);
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void closeClientSocket() {
        try {
            clientSocket.close();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't close client socket");
        }
    }

    //TODO: replace this goofy aah message with binary protocol or JSON packets
    //TODO: Message Struct
    private String prefixMessage(String name, String message) {
        return String.format("[%s]: %s", name, message);
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


    private void connect(String serverIp, int serverPort)  {
        try {
            clientSocket = new Socket(serverIp, serverPort);
        }catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't initiate socket to the Server");
        }
    }




    private void startListener() {
        new Thread(this::listen).start();
    }

    private void listen() {
        String msg;
        try {
            while((msg = in.readLine()) != null) {
                if(listener != null) {
                    listener.onMessage(msg);
                }
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while collecting server message");
        }
    }
}
