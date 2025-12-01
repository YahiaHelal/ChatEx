package com.yahia.anotherchatapplicatoin.server;

import com.yahia.anotherchatapplicatoin.handlers.ServerClientHandler;
import com.yahia.anotherchatapplicatoin.managers.LogManager;
import com.yahia.anotherchatapplicatoin.scenes.LoginScene;
import com.yahia.anotherchatapplicatoin.utils.backend.SocketUtils;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void broadCastMessage(String message) {
        for(ServerClientHandler client: CLIENTS) {
            client.sendMessage(message);
        }
    }


    private void run() throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        LOGGER.log(Level.INFO, String.format("Server running on %s:%d", serverSocket.getInetAddress().getHostAddress(), SERVER_PORT));
    }
    private void addClient(ServerClientHandler clientHandler) {
        CLIENTS.add(clientHandler);
    }

    //TODO: client can disconnect form the server, should rollback ui to login screen
    private void removeClient(ServerClientHandler clientHandler) {
        CLIENTS.remove(clientHandler);
    }


    private void listen(){
        new Thread(() -> {
            while(true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ServerClientHandler clientHandler = new ServerClientHandler(clientSocket, this);

                    //BUG: same client info with different object reference will be stored normally
                    addClient(clientHandler);

                    LOGGER.log(Level.FINE, String.format("Number of Clients connected to %s is %d", serverSocket.getInetAddress().getHostAddress(), CLIENTS.size()));
                    new Thread(clientHandler).start();
                }catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Server couldn't connect to client");
                }
            }
        }).start();
    }




}
