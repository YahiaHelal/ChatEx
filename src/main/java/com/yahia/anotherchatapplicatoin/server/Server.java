package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.handlers.ServerClientHandler;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketEncoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.BroadCastMessage;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageSender;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketHandlerRegistry;
import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageReceiver;
import com.yahia.anotherchatapplicatoin.transport.tcp.SocketMessageSender;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeRequest;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeResponse;
import com.yahia.anotherchatapplicatoin.utils.network.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final int SERVER_PORT;
    private final Set<ServerClientHandler> CLIENTS;
    private final Set<String> CLIENT_NAMES;
    private final Logger LOGGER;
    private ServerSocket serverSocket;
    private final PacketHandlerRegistry handlerRegistry;
    private Socket clientSocket; // NOTE: overwritten with each new connection
    private MessageSender sender; //NOTE: overwritten with each new connection
    private MessageReceiver receiver; //NOTE: overwritten with each new connection


    //TODO: new server fetches the sent messages from db when initialized
    //TODO: each server deals with it's own data transfer currently
    //TODO: system-wise responsibility
    public Server(int serverPort) {
        this.SERVER_PORT = serverPort;
        CLIENTS = ConcurrentHashMap.newKeySet();
        CLIENT_NAMES = ConcurrentHashMap.newKeySet();
        LOGGER = LogManager.getLogger();

        handlerRegistry = new PacketHandlerRegistry();
        registerHandlers();
    }

    public void start() {
        try {
            run();
            listen();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, String.format("Error creating server socket %s:%d", serverSocket.getInetAddress().getHostAddress(), this.SERVER_PORT));
        }
    }

    public String getServerAddress() {
        return SocketUtils.getServerSocketAddress(serverSocket);
    }
    public int getServerPort() {
        return SERVER_PORT;
    }

    public void broadCastPacket(CommunicationPacket packet) {
        for(ServerClientHandler clientHandler: CLIENTS) {
            clientHandler.sendMessageToClient(packet);
        }
    }

    //TODO: client can disconnect form the server, should rollback ui to login screen
    //TODO: when disconnected, server should broadcast the info
    //TODO: use Disconnect Request-Response records
    public void removeClient(ServerClientHandler clientHandler, String clientUsername) {
        CLIENTS.remove(clientHandler);
        CLIENT_NAMES.remove(clientUsername);
        LOGGER.log(Level.INFO, String.format("%s has disconnected", clientUsername));
        String info = JsonHelper.GSON.toJson(new BroadCastMessage("SERVER", String.format("%s has been disconnected", clientUsername)));
        broadCastPacket(new CommunicationPacket(PacketType.BROADCAST_MESSAGE, info));
    }


    private void registerHandlers() {
        handlerRegistry.register(PacketType.HANDSHAKE_REQUEST, this::handleHandshakeRequest);
    }


    private void run() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", SocketUtils.getServerSocketAddress(serverSocket), SERVER_PORT));
    }

    private ConnectionStatus checkStatus(HandshakeRequest handShakeRequest) {
        if(CLIENT_NAMES.contains(handShakeRequest.username())) {
            return ConnectionStatus.REJECT_USERNAME_TAKEN;
        }
        CLIENT_NAMES.add(handShakeRequest.username());
        return ConnectionStatus.ACCEPT;
    }

    private void handleHandshakeRequest(CommunicationPacket packet) throws IOException {
        LOGGER.log(Level.INFO, "Server Receives a HandShake Request");
        HandshakeRequest request = JsonHelper.GSON.fromJson(packet.payload(), HandshakeRequest.class);
        ConnectionStatus status = checkStatus(request);
        String response = JsonHelper.GSON.toJson(new HandshakeResponse(status));

        sender.send(new CommunicationPacket(PacketType.HANDSHAKE_RESPONSE, response));

        if(status == ConnectionStatus.ACCEPT) {
            ServerClientHandler clientHandler = new ServerClientHandler(clientSocket, this, request.username());
            CLIENTS.add(clientHandler);
            LOGGER.log(Level.FINE, String.format("Number of Clients connected to %s is %d", serverSocket.getInetAddress().getHostAddress(), CLIENTS.size()));
            new Thread(clientHandler).start();
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        sender = new SocketMessageSender(new PrintWriter(clientSocket.getOutputStream(), true), new JsonPacketEncoder());
        receiver = new SocketMessageReceiver(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), new JsonPacketDecoder());
        CommunicationPacket clientSentPacket = receiver.receive();
        handlerRegistry.get(clientSentPacket.type()).handlePacket(clientSentPacket);
    }

    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Server couldn't connect to client");
                }
            }
        }).start();
    }




}
