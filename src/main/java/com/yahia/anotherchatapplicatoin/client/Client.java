package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.client.exceptions.ClientDisconnectedException;
import com.yahia.anotherchatapplicatoin.client.listeners.HandShakeListener;
import com.yahia.anotherchatapplicatoin.client.listeners.MessageListener;
import com.yahia.anotherchatapplicatoin.client.listeners.ServerEventsListener;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
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
    private ServerEventsListener serverEventsListener;
    private volatile DisconnectReason disconnectReason;

    //TODO: client fetches the server's old messages when connected
    //TODO: introduce ClientController that implements ClientListener
    public Client(String clientName, String serverIp, int port) throws IOException {
        this.clientName = clientName;
        startNewClient(serverIp, port);
    }

    public void setMessageHandler(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setHandShakeHandler(HandShakeListener handShakeListener) {
        this.handShakeListener = handShakeListener;
    }

    public void setServerEventsListener(ServerEventsListener serverEventsListener) {
        this.serverEventsListener = serverEventsListener;
    }

    public void sendMessage(String message) {
        if(clientSocket == null || clientSocket.isClosed()) {
            LOGGER.log(Level.SEVERE, String.format("%s tried to send a message through a closed socket ", clientName));
            return;
        }
        out.println(message);
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getClientName() {
        return clientName;
    }

    private void closeClientSocket() {
        try {
            LOGGER.log(Level.INFO, "Closing client socket");
            clientSocket.close();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Couldn't close client socket");
        }
    }

    public void disconnect() {
        requestDisconnect();
        closeClientSocket();
    }

    //TODO: show disclaimer to the client before closing the window
    private void requestDisconnect() {
        String info = JsonHelper.GSON.toJson(new DisconnectRequest(clientName));
        sendMessage(JsonHelper.GSON.toJson(new CommunicationPacket(MessageType.DISCONNECT_REQUEST, info)));

    }

    public void broadCastUserMessage(String text) {
        broadCastInternal(clientName, text);
    }

    public void broadCastSystemMessage(String text) {
        broadCastInternal("SERVER", text);
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
    protected void connect(String serverIp, int serverPort) throws IOException {
        clientSocket = new Socket(serverIp, serverPort);
    }

    @Override
    protected void startListener() {
        new Thread(this::listen, "client-listener-thread").start();
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
        }finally {
            setDisconnectReason(DisconnectReason.SERVER_SHUTDOWN);
            notifyDisconnect(disconnectReason);
        }

    }

    //TODO: isn't it a bit dangerous ? everyone can set the disconnection reason
    public void setDisconnectReason(DisconnectReason reason) {
        if(disconnectReason == null) disconnectReason = reason;
    }
    private void handleHandShakeResponse(CommunicationPacket packet) {
        HandShakeResponse handShakeResponse = JsonHelper.GSON.fromJson(packet.payload(), HandShakeResponse.class);
        if(handShakeResponse.status() != ConnectionStatus.ACCEPT) {
            setDisconnectReason(DisconnectReason.HANDSHAKE_FAILED);
            closeClientSocket();
        }
        notifyHandShakeListener(handShakeResponse.status());
    }

    private void handleBroadCastMessage(CommunicationPacket packet) {
        BroadCastMessage broadCastMessage = JsonHelper.GSON.fromJson(packet.payload(), BroadCastMessage.class);
        notifyMessageListener(broadCastMessage);
    }

    private void notifyDisconnect(DisconnectReason reason) {
        if(serverEventsListener != null) {
            LOGGER.log(Level.INFO, String.format("notifying the server for event %s", reason));
            serverEventsListener.onDisconnect(reason);
        }
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

    private void broadCastInternal(String sender, String text) {
        BroadCastMessage broadCastMessage = new BroadCastMessage(sender, text);
        CommunicationPacket broadCastPacket = new CommunicationPacket(MessageType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(broadCastMessage));
        sendMessage(JsonHelper.GSON.toJson(broadCastPacket));
    }

    private String prefixName(String name, String message) {
        return String.format("[%s]: %s", name, message);
    }
}
