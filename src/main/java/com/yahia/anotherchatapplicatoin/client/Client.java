package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.client.listeners.HandShakeListener;
import com.yahia.anotherchatapplicatoin.client.listeners.MessageListener;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeResponse;
import com.yahia.anotherchatapplicatoin.protocol.BroadCastMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends AbstractClient{
    private final Logger LOGGER = LogManager.getLogger();
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;
    private String clientName;
    private MessageListener messageListener;
    private HandShakeListener handShakeListener;

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
    public void setHandShakeListener(HandShakeListener handShakeListener) {
        this.handShakeListener = handShakeListener;
    }

    //TODO: client fetches the server's old messages when connected
    //TODO: introduce ClientController that implements ClientListener
    public Client(String clientName, String serverIp, int port) {
        this.clientName = clientName;
        startNewClient(serverIp, port);
    }

    public void sendMessage(String message) {
        out.println(message);
    }


    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void closeClientSocket() {
        try {
            LOGGER.log(Level.INFO, "Closing client socket");
            clientSocket.close();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't close client socket");
        }
    }



    @Override
    protected void initMessengers(){
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            LOGGER.log(Level.INFO, "Messengers initialized");
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while initializing messengers");
        }

    }


    @Override
    protected void connect(String serverIp, int serverPort)  {
        try {
            clientSocket = new Socket(serverIp, serverPort);
        }catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't initiate socket to the Server");
        }
    }
    @Override
    protected void startListener() {
        new Thread(this::listen).start();
    }


    private void listen() {
        String msg;
        try {
            while((msg = in.readLine()) != null) {
                LOGGER.log(Level.INFO, String.format("Message sent to client : %s", msg));
                CommunicationPacket serverSentPacket = JsonHelper.GSON.fromJson(msg, CommunicationPacket.class);
                //TODO: apply OCP later
                switch (serverSentPacket.type()) {
                    case HANDSHAKE_RESPONSE -> handleHandShakeResponse(serverSentPacket);
                    case BROADCAST_MESSAGE -> handleBroadCastMessage(serverSentPacket);
                }
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Socket between client and server is closed");
        }
    }

    private void handleHandShakeResponse(CommunicationPacket packet) {
        HandShakeResponse handShakeResponse = JsonHelper.GSON.fromJson(packet.payload(), HandShakeResponse.class);

        if(handShakeResponse.status() != ConnectionStatus.ACCEPT) {
            closeClientSocket();
        }
        notifyHandShakeListener(handShakeResponse.status());
    }

    private void notifyHandShakeListener(ConnectionStatus status) {
        if(handShakeListener != null) {
            handShakeListener.onHandShake(status);
        }
    }

    private void notifyMessageListener(BroadCastMessage message) {
        if(messageListener != null) {
            messageListener.onMessage(prefixName(message.sender(), message.text()));
        }
    }

    private void handleBroadCastMessage(CommunicationPacket packet) {
        BroadCastMessage broadCastMessage = JsonHelper.GSON.fromJson(packet.payload(), BroadCastMessage.class);
        notifyMessageListener(broadCastMessage);
    }


    private String prefixName(String name, String message) {
        return String.format("[%s]: %s", name, message);
    }
}
