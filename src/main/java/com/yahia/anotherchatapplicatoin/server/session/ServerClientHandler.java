package com.yahia.anotherchatapplicatoin.server.session;

import com.yahia.anotherchatapplicatoin.protocol.codec.packet.json.JsonPacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.codec.packet.json.JsonPacketEncoder;
import com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.disconnect.JsonDisconnectRequestDecoder;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectRequest;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageSender;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketHandlerRegistry;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageReceiver;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageSender;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.server.Server;
import com.yahia.anotherchatapplicatoin.utils.network.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerClientHandler implements Runnable {
    private final Logger LOGGER;
    private final MessageSender sender;
    private final MessageReceiver receiver;
    private final Server CHAT_SERVER;
    private String clientUsername;
    private final PacketHandlerRegistry handlerRegistry;

    //NOTE: only fired when a new client is authenticated by the HandShake
    public ServerClientHandler(Socket clientSocket, Server chatServer, String clientUsername) throws IOException {
        sender = new SocketMessageSender(new PrintWriter(clientSocket.getOutputStream(), true), new JsonPacketEncoder());
        receiver = new SocketMessageReceiver(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), new JsonPacketDecoder());
        this.CHAT_SERVER = chatServer;
        this.clientUsername = clientUsername;
        LOGGER = LogManager.getLogger();
        handlerRegistry = new PacketHandlerRegistry();
        registerHandlers();
    }

    //TODO: replace socket dependency with socket sender interface
    public void sendMessageToClient(CommunicationPacket packet) {
        sender.send(packet);
    }


    //TODO: Include system messages, like when a client changes it's username
    private void registerHandlers() {
        handlerRegistry.register(PacketType.BROADCAST_MESSAGE, this::handleBroadCast);
        handlerRegistry.register(PacketType.DISCONNECT_REQUEST, this::handleDisconnection);
        handlerRegistry.register(PacketType.PRIVATE_MESSAGE, this::handlePrivateMessage);
    }
    private void updateClientUsername(String newName) {
        this.clientUsername = newName;
    }

    private void handleBroadCast(CommunicationPacket packet){
        CHAT_SERVER.broadCastPacket(packet);
    };

    //TODO: private messages between friends only
    private void handlePrivateMessage(CommunicationPacket packet) {

    }
    private void handleDisconnection(CommunicationPacket packet) {
        JsonDisconnectRequestDecoder decoder = new JsonDisconnectRequestDecoder();
        DisconnectRequest request = decoder.decode(packet.payload());
        CHAT_SERVER.removeClient(this, request.username());
    }

    @Override
    public void run() {
        CommunicationPacket clientPacket;
        try {
            while((clientPacket = receiver.receive()) != null) {
                handlerRegistry.get(clientPacket.type()).handlePacket(clientPacket);
            }
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't receive client message");
        }
    }


}
