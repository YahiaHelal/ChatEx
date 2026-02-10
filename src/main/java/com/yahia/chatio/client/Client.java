package com.yahia.chatio.client;

import com.yahia.chatio.client.base.AbstractClient;
import com.yahia.chatio.client.listeners.HandshakeListener;
import com.yahia.chatio.client.listeners.MessageListener;
import com.yahia.chatio.client.listeners.DisconnectListener;
import com.yahia.chatio.protocol.codec.packet.json.JsonPacketDecoder;
import com.yahia.chatio.protocol.codec.packet.json.JsonPacketEncoder;
import com.yahia.chatio.protocol.codec.payload.json.disconnect.JsonDisconnectRequestEncoder;
import com.yahia.chatio.protocol.codec.payload.json.fin.JsonFinDecoder;
import com.yahia.chatio.protocol.codec.payload.json.handshake.JsonHandshakeResponseDecoder;
import com.yahia.chatio.protocol.codec.payload.json.messaging.JsonBroadcastMessageDecoder;
import com.yahia.chatio.protocol.codec.payload.json.messaging.JsonBroadcastMessageEncoder;
import com.yahia.chatio.protocol.disconnect.DisconnectReason;
import com.yahia.chatio.protocol.disconnect.DisconnectRequest;
import com.yahia.chatio.protocol.handshake.ConnectionStatus;
import com.yahia.chatio.protocol.messaging.MessageReceiver;
import com.yahia.chatio.protocol.messaging.MessageSender;
import com.yahia.chatio.protocol.packet.CommunicationPacket;
import com.yahia.chatio.protocol.packet.PacketHandlerRegistry;
import com.yahia.chatio.protocol.packet.PacketType;
import com.yahia.chatio.transport.tcp.SocketMessageReceiver;
import com.yahia.chatio.transport.tcp.SocketMessageSender;
import com.yahia.chatio.utils.logging.LogManager;
import com.yahia.chatio.protocol.handshake.HandshakeResponse;
import com.yahia.chatio.protocol.messaging.BroadCastMessage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends AbstractClient {
    private final Logger LOGGER = LogManager.getLogger();
    private Socket clientSocket;
    private String clientName;
    private MessageListener messageListener;
    private HandshakeListener handShakeListener;
    private DisconnectListener disconnectListener;
    private MessageSender sender;
    private MessageReceiver receiver;
    private volatile DisconnectReason disconnectReason;
    private final PacketHandlerRegistry handlerRegistry;


    
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

    public void setDisconnectListener(DisconnectListener serverEventsListener) {
        this.disconnectListener = serverEventsListener;
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
        notifyDisconnectListener(reason);
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
        handlerRegistry.register(PacketType.FIN, this::handleServerShutdown);
    }


    private void listen() {
        CommunicationPacket packet;
        try {
            while((packet = receiver.receive()) != null) {
                LOGGER.log(Level.INFO, String.format("Message: %s sent to client %s", packet, clientName));
                handlerRegistry.get(packet.type()).handlePacket(packet);
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Client socket has been closed: %s", e.getMessage()));
        }

    }

    private void setDisconnectReason(DisconnectReason reason) {
        if(disconnectReason == null) disconnectReason = reason;
    }

    private void requestDisconnect() {
        JsonDisconnectRequestEncoder encoder = new JsonDisconnectRequestEncoder();
        String info = encoder.encode(new DisconnectRequest(clientName));
        sendMessage(new CommunicationPacket(PacketType.DISCONNECT_REQUEST, info));
    }

    private void handleHandShakeResponse(CommunicationPacket packet) {
        JsonHandshakeResponseDecoder decoder = new JsonHandshakeResponseDecoder();
        HandshakeResponse handShakeResponse = decoder.decode(packet.payload());
        notifyHandShakeListener(handShakeResponse.status());
    }

    private void handleBroadCastMessage(CommunicationPacket packet) {
        JsonBroadcastMessageDecoder decoder = new JsonBroadcastMessageDecoder();
        BroadCastMessage broadCastMessage = decoder.decode(packet.payload());
        notifyMessageListener(broadCastMessage);
    }

    private void handleServerShutdown(CommunicationPacket packet) {
        JsonFinDecoder decoder = new JsonFinDecoder();
        LOGGER.log(Level.SEVERE, String.format("Server %s dies", decoder.decode(packet.payload())));
        disconnect(DisconnectReason.SERVER_SHUTDOWN);
    }

    private void notifyDisconnectListener(DisconnectReason reason) {
        if(disconnectListener != null) {
            disconnectListener.onDisconnect(reason);
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
        JsonBroadcastMessageEncoder encoder = new JsonBroadcastMessageEncoder();
        BroadCastMessage msg = new BroadCastMessage(sender, text);
        sendMessage(new CommunicationPacket(PacketType.BROADCAST_MESSAGE, encoder.encode(msg)));
    }

    private String prefixName(String name, String message) {
        return String.format("[%s]: %s", name, message);
    }
}
