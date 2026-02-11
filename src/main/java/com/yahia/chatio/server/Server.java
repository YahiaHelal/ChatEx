package com.yahia.chatio.server;

import com.yahia.chatio.protocol.codec.payload.json.fin.JsonFinEncoder;
import com.yahia.chatio.protocol.codec.payload.json.handshake.JsonHandshakeRequestDecoder;
import com.yahia.chatio.protocol.codec.payload.json.handshake.JsonHandshakeResponseEncoder;
import com.yahia.chatio.protocol.codec.payload.json.messaging.JsonBroadcastMessageEncoder;
import com.yahia.chatio.protocol.server.ServerConnection;
import com.yahia.chatio.protocol.terminate.FinPacket;
import com.yahia.chatio.server.accept.ServerHandshakeContext;
import com.yahia.chatio.server.accept.ServerPacketHandlerRegistry;
import com.yahia.chatio.server.session.ServerClientHandler;
import com.yahia.chatio.protocol.codec.packet.json.JsonPacketDecoder;
import com.yahia.chatio.protocol.codec.packet.json.JsonPacketEncoder;
import com.yahia.chatio.protocol.handshake.ConnectionStatus;
import com.yahia.chatio.protocol.messaging.BroadCastMessage;
import com.yahia.chatio.protocol.messaging.MessageReceiver;
import com.yahia.chatio.protocol.messaging.MessageSender;
import com.yahia.chatio.protocol.packet.CommunicationPacket;
import com.yahia.chatio.protocol.packet.PacketType;
import com.yahia.chatio.transport.tcp.SocketMessageReceiver;
import com.yahia.chatio.transport.tcp.SocketMessageSender;
import com.yahia.chatio.utils.logging.LogManager;
import com.yahia.chatio.protocol.handshake.HandshakeRequest;
import com.yahia.chatio.protocol.handshake.HandshakeResponse;
import com.yahia.chatio.utils.network.SocketUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: make the server more like an API, that handles jobs like register/un-register clients, processing handshakes and stuff
public class Server {
    private volatile boolean running;

    private final ServerConnection serverConnection;
    private final Set<ServerClientHandler> CLIENTS;
    private final Set<String> CLIENT_NAMES;
    private final Logger LOGGER;
    private ServerSocket serverSocket;
    private final ServerPacketHandlerRegistry serverHandlerRegistry;

    //TODO: new server fetches the sent messages from db when initialized
    //TODO: each server deals with it's own data transfer currently
    //TODO: system-wise responsibility
    public Server(ServerConnection connection){
        this.serverConnection = connection;
        CLIENTS = ConcurrentHashMap.newKeySet();
        CLIENT_NAMES = ConcurrentHashMap.newKeySet();
        LOGGER = LogManager.getLogger();
        serverHandlerRegistry = new ServerPacketHandlerRegistry();
        registerHandlers();
    }


    //TODO: better way instead of sending dummy data
    public void broadcastTermination() {
        JsonFinEncoder encoder = new JsonFinEncoder();
        String info = encoder.encode(new FinPacket(serverConnection.name()));
        broadCastPacket(new CommunicationPacket(PacketType.FIN, info));
    }


    public synchronized void terminate() throws IOException {
        running = false;
        if(serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public String getIp() {
        return SocketUtils.getServerSocketAddress(serverSocket);
    }
    public int getPort() {
        return serverConnection.port();
    }


    public synchronized void start() throws IOException {
        if(running) return;
        run();
        running = true;
        listen();
    }

    public void broadCastPacket(CommunicationPacket packet) {
        for(ServerClientHandler clientHandler: CLIENTS) {
            clientHandler.sendMessageToClient(packet);
        }
    }

    public void removeClient(ServerClientHandler clientHandler, String clientUsername) {
        if(!CLIENT_NAMES.contains(clientUsername)) return;
        CLIENTS.remove(clientHandler);
        CLIENT_NAMES.remove(clientUsername);
        LOGGER.log(Level.INFO, String.format("%s has disconnected", clientUsername));
        JsonBroadcastMessageEncoder encoder = new JsonBroadcastMessageEncoder();
        String info = encoder.encode(new BroadCastMessage("SERVER", String.format("%s has been disconnected", clientUsername)));
        broadCastPacket(new CommunicationPacket(PacketType.BROADCAST_MESSAGE, info));
    }

    private void registerClient(String username, ServerClientHandler handler) {
        CLIENTS.add(handler);
        CLIENT_NAMES.add(username);
    }

    private void registerHandlers() {
        serverHandlerRegistry.register(PacketType.HANDSHAKE_REQUEST, this::handleHandshakeRequest);
    }


    private void run() throws IOException {
        serverSocket = new ServerSocket(serverConnection.port());
        LOGGER.log(Level.INFO, String.format("Server running on %s", serverConnection.id()));
    }

    private ConnectionStatus reserveUsername(String username) {
        return CLIENT_NAMES.add(username)
                ? ConnectionStatus.ACCEPT
                : ConnectionStatus.REJECT_USERNAME_TAKEN;
    }

    private void rollbackUsername(String username) {
        CLIENT_NAMES.remove(username);
    }

    private void handleAccept(ServerHandshakeContext ctx, HandshakeRequest request) throws IOException {
        ServerClientHandler clientHandler = new ServerClientHandler(ctx.clientSocket(), this, request.username());
        registerClient(request.username(), clientHandler);
        LOGGER.log(Level.FINE, String.format("Number of Clients connected to %s is %d", serverSocket.getInetAddress().getHostAddress(), CLIENTS.size()));
        new Thread(clientHandler, "server-client-handler-thread").start();

    }



    private void handleHandshakeRequest(ServerHandshakeContext ctx) throws IOException {
        LOGGER.log(Level.INFO, "Server Receives a HandShake Request");
        JsonHandshakeRequestDecoder decoder = new JsonHandshakeRequestDecoder();
        JsonHandshakeResponseEncoder encoder = new JsonHandshakeResponseEncoder();

        CommunicationPacket packet = ctx.receiver().receive();

        HandshakeRequest request = decoder.decode(packet.payload());
        ConnectionStatus status = reserveUsername(request.username());

        String response = encoder.encode(new HandshakeResponse(status));

        ctx.sender().send(new CommunicationPacket(PacketType.HANDSHAKE_RESPONSE, response));

        LOGGER.log(Level.INFO, String.format("Handshake status: %s", status));
        if(status == ConnectionStatus.ACCEPT) {
           handleAccept(ctx, request);
        }
    }

    private void handleClient(Socket clientSocket)  {
        try {
            MessageSender sender = new SocketMessageSender(new PrintWriter(clientSocket.getOutputStream(), true), new JsonPacketEncoder());
            MessageReceiver receiver = new SocketMessageReceiver(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), new JsonPacketDecoder());
            handleHandshakeRequest(new ServerHandshakeContext(sender, receiver, clientSocket));
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed while handling client handshake");
        }
    }

    private void listen(){
        new Thread(() -> {
            while(running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOGGER.log(Level.INFO, "Server receives a new connection");
                    new Thread(() -> handleClient(clientSocket), "server-handshake-thread").start();
                }catch (IOException e) {
                    if(running) {
                        LOGGER.log(Level.WARNING, "Server couldn't connect to client", e);
                    }
                    break;
                }
            }
        }, "server-accept-thread").start();
    }

    
}
