package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.server.accept.ServerConnectionContext;
import com.yahia.anotherchatapplicatoin.server.accept.ServerPacketHandlerRegistry;
import com.yahia.anotherchatapplicatoin.server.session.ServerClientHandler;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.codec.JsonPacketEncoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.BroadCastMessage;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageSender;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
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

//TODO: make the server more like an API, that handles jobs like register/un-register clients, processing handshakes and stuff
public class Server {

    private final int SERVER_PORT;
    private final Set<ServerClientHandler> CLIENTS;
    private final Set<String> CLIENT_NAMES;
    private final Logger LOGGER;
    private ServerSocket serverSocket;
    private final ServerPacketHandlerRegistry serverHandlerRegistry;

    //TODO: new server fetches the sent messages from db when initialized
    //TODO: each server deals with it's own data transfer currently
    //TODO: system-wise responsibility
    public Server(int serverPort) {
        this.SERVER_PORT = serverPort;
        CLIENTS = ConcurrentHashMap.newKeySet();
        CLIENT_NAMES = ConcurrentHashMap.newKeySet();
        LOGGER = LogManager.getLogger();

        serverHandlerRegistry = new ServerPacketHandlerRegistry();
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

    public void broadCastPacket(CommunicationPacket packet) {
        for(ServerClientHandler clientHandler: CLIENTS) {
            clientHandler.sendMessageToClient(packet);
        }
    }

    public void removeClient(ServerClientHandler clientHandler, String clientUsername) {
        CLIENTS.remove(clientHandler);
        CLIENT_NAMES.remove(clientUsername);
        LOGGER.log(Level.INFO, String.format("%s has disconnected", clientUsername));
        String info = JsonHelper.GSON.toJson(new BroadCastMessage("SERVER", String.format("%s has been disconnected", clientUsername)));
        broadCastPacket(new CommunicationPacket(PacketType.BROADCAST_MESSAGE, info));
    }

    public void registerClient(String username, ServerClientHandler handler) {
        CLIENTS.add(handler);
        CLIENT_NAMES.add(username);
    }


    private void registerHandlers() {
        serverHandlerRegistry.register(PacketType.HANDSHAKE_REQUEST, this::handleHandshakeRequest);
    }


    private void run() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", SocketUtils.getServerSocketAddress(serverSocket), SERVER_PORT));
    }

    private ConnectionStatus checkStatus(HandshakeRequest handShakeRequest) {
        if(CLIENT_NAMES.contains(handShakeRequest.username())) {
            return ConnectionStatus.REJECT_USERNAME_TAKEN;
        }
        return ConnectionStatus.ACCEPT;
    }

    private void handleAccept(ServerConnectionContext ctx, HandshakeRequest request) throws IOException {
        ServerClientHandler clientHandler = new ServerClientHandler(ctx.clientSocket(), this, request.username());
        registerClient(request.username(), clientHandler);
        LOGGER.log(Level.FINE, String.format("Number of Clients connected to %s is %d", serverSocket.getInetAddress().getHostAddress(), CLIENTS.size()));
        new Thread(clientHandler).start();
    }

    private void handleHandshakeRequest(ServerConnectionContext ctx) throws IOException {
        CommunicationPacket packet = ctx.receiver().receive();

        LOGGER.log(Level.INFO, "Server Receives a HandShake Request");
        HandshakeRequest request = JsonHelper.GSON.fromJson(packet.payload(), HandshakeRequest.class);
        ConnectionStatus status = checkStatus(request);
        String response = JsonHelper.GSON.toJson(new HandshakeResponse(status));

        ctx.sender().send(new CommunicationPacket(PacketType.HANDSHAKE_RESPONSE, response));

        if(status == ConnectionStatus.ACCEPT) {
           handleAccept(ctx, request);
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
       MessageSender sender = new SocketMessageSender(new PrintWriter(clientSocket.getOutputStream(), true), new JsonPacketEncoder());
       MessageReceiver receiver = new SocketMessageReceiver(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), new JsonPacketDecoder());
       handleHandshakeRequest(new ServerConnectionContext(sender, receiver, clientSocket));
    }

    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Server couldn't connect to client");
                }
            }
        }).start();
    }




}
