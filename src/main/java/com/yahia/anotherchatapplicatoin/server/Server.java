package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.handlers.ServerClientHandler;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.protocol.*;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeRequest;
import com.yahia.anotherchatapplicatoin.protocol.HandShakeResponse;
import com.yahia.anotherchatapplicatoin.utils.backend.SocketUtils;

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


    //TODO: new server fetches the sent messages from db when initialized
    //TODO: each server deals with it's own data transfer currently
    //TODO: system-wise responsibility
    public Server(int serverPort) {
        this.SERVER_PORT = serverPort;
        CLIENTS = ConcurrentHashMap.newKeySet();
        CLIENT_NAMES = ConcurrentHashMap.newKeySet();
        LOGGER = LogManager.getLogger();
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
    public void sendMessage(Socket clientSocket, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        }catch (IOException e) {
            LOGGER.log(Level.WARNING, "Server couldn't send the message %s", message);
        }

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
        broadCastPacket(new CommunicationPacket(MessageType.BROADCAST_MESSAGE, info));
    }




    private void run() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
    }

    private ConnectionStatus handleHandShake(HandShakeRequest handShakeRequest) {
        LOGGER.log(Level.INFO, "Server Receives a HandShake Request");
        if(CLIENT_NAMES.contains(handShakeRequest.username())) {
            return ConnectionStatus.REJECT_USERNAME_TAKEN;
        }
        CLIENT_NAMES.add(handShakeRequest.username());
        return ConnectionStatus.ACCEPT;
    }

    private void greetClient(String clientUsername) {
        String msg = String.format("%s Has Joined The Chat Room, Greet the hell out of em", clientUsername);
        String greet = JsonHelper.GSON.toJson(new BroadCastMessage("SERVER", msg));
        broadCastPacket(new CommunicationPacket(MessageType.BROADCAST_MESSAGE, greet));
    }

    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader tempIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter tempOut = new PrintWriter(clientSocket.getOutputStream(), true);

                    // receives the Header first
                    CommunicationPacket clientSentPacket = JsonHelper.GSON.fromJson(tempIn.readLine(), CommunicationPacket.class);
                    switch(clientSentPacket.type()) {
                        case HANDSHAKE_REQUEST -> {
                            HandShakeRequest handShakeRequest = JsonHelper.GSON.fromJson(clientSentPacket.payload(), HandShakeRequest.class);
                            ConnectionStatus connectionStatus = handleHandShake(handShakeRequest);
                            String response = JsonHelper.GSON.toJson(new HandShakeResponse(connectionStatus));
                            CommunicationPacket handShakeResponsePacket = new CommunicationPacket(MessageType.HANDSHAKE_RESPONSE, response);

                            tempOut.println(JsonHelper.GSON.toJson(handShakeResponsePacket));

                            if(connectionStatus == ConnectionStatus.ACCEPT) {
                                ServerClientHandler clientHandler = new ServerClientHandler(clientSocket, this, handShakeRequest.username());
                                CLIENTS.add(clientHandler);
                                LOGGER.log(Level.FINE, String.format("Number of Clients connected to %s is %d", serverSocket.getInetAddress().getHostAddress(), CLIENTS.size()));
                                new Thread(clientHandler).start();
                                //FIX: greetings not appearing for the first client, fired before chat scene loads
                                greetClient(handShakeRequest.username());
                            }
                        }
                        default -> {
                            LOGGER.log(Level.SEVERE, "UNKNOWN HEADER");
                        }

                    }
                }catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Server couldn't connect to client");
                }
            }
        }).start();
    }




}
