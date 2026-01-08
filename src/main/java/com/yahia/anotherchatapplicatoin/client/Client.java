package com.yahia.anotherchatapplicatoin.client;

import com.yahia.anotherchatapplicatoin.client.listeners.HandshakeListener;
import com.yahia.anotherchatapplicatoin.client.listeners.MessageListener;
import com.yahia.anotherchatapplicatoin.client.listeners.DisconnectListener;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketEncoder;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectReason;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectRequest;
import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageSender;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketHandlerRegistry;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageReceiver;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageSender;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeResponse;
import com.yahia.anotherchatapplicatoin.protocol.messaging.BroadCastMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends AbstractClient{
    private final Logger LOGGER = LogManager.getLogger();
    private Socket clientSocket;
    private String clientName;
    private MessageListener messageListener;
    private HandshakeListener handShakeListener;
    private DisconnectListener serverEventsListener;
    private volatile DisconnectReason disconnectReason;
    private final PacketHandlerRegistry handlerRegistry;
    private MessageSender sender;
    private MessageReceiver receiver;
    //TODO: client fetches the server's old messages when connected
    public Client(String clientName, String serverIp, int port) throws IOException {
        this.clientName = clientName;
        handlerRegistry = new PacketHandlerRegistry();
        startNewClient(serverIp, port);
    }

    public void setMessageHandler(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setHandShakeHandler(HandshakeListener handShakeListener) {
        this.handShakeListener = handShakeListener;
    }

    public void setServerEventsListener(DisconnectListener serverEventsListener) {
        this.serverEventsListener = serverEventsListener;
    }

    public void sendMessage(CommunicationPacket packet) {
        if(clientSocket == null || clientSocket.isClosed()) {
            LOGGER.log(Level.SEVERE, String.format("%s tried to send a message through a closed socket ", clientName));
            return;
        }
        sender.send(packet);
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

    public void disconnect(DisconnectReason reason) {
        setDisconnectReason(reason);
        if(reason != DisconnectReason.SERVER_SHUTDOWN && reason != DisconnectReason.HANDSHAKE_FAILED) {
            requestDisconnect();
        }
        closeClientSocket();
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
            sender = new SocketMessageSender(new PrintWriter(clientSocket.getOutputStream(), true), new JsonPacketEncoder());
            receiver = new SocketMessageReceiver(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), new JsonPacketDecoder());
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

    @Override
    protected void registerHandlers() {
        handlerRegistry.register(PacketType.HANDSHAKE_RESPONSE, this::handleHandShakeResponse);
        handlerRegistry.register(PacketType.BROADCAST_MESSAGE, this::handleBroadCastMessage);
    }

    private void listen() {
        CommunicationPacket packet;
        try {
            while((packet = receiver.receive()) != null) {
                LOGGER.log(Level.INFO, String.format("Message: %s sent to client %s", packet, clientName));
                handlerRegistry.get(packet.type()).handlePacket(packet);
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Client socket has been closed");
        }finally {
            LOGGER.log(Level.SEVERE, "Server dies");
            disconnect(DisconnectReason.SERVER_SHUTDOWN);
            notifyDisconnectListener(disconnectReason);
        }

    }

    private void setDisconnectReason(DisconnectReason reason) {
        if(disconnectReason == null) disconnectReason = reason;
    }

    private void requestDisconnect() {
        String info = JsonHelper.GSON.toJson(new DisconnectRequest(clientName));
        sendMessage(new CommunicationPacket(PacketType.DISCONNECT_REQUEST, info));
    }

    private void handleHandShakeResponse(CommunicationPacket packet) {
        HandshakeResponse handShakeResponse = JsonHelper.GSON.fromJson(packet.payload(), HandshakeResponse.class);
        notifyHandShakeListener(handShakeResponse.status());
    }

    private void handleBroadCastMessage(CommunicationPacket packet) {
        BroadCastMessage broadCastMessage = JsonHelper.GSON.fromJson(packet.payload(), BroadCastMessage.class);
        notifyMessageListener(broadCastMessage);
    }

    private void notifyDisconnectListener(DisconnectReason reason) {
        if(serverEventsListener != null) {
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
        sendMessage(new CommunicationPacket(PacketType.BROADCAST_MESSAGE, JsonHelper.GSON.toJson(broadCastMessage)));
    }

    private String prefixName(String name, String message) {
        return String.format("[%s]: %s", name, message);
    }
}
