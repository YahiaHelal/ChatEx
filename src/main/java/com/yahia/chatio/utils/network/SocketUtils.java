package com.yahia.chatio.utils.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SocketUtils {

    public static String getSocketAddress(Socket socket) {
        return socket.getInetAddress().getHostAddress();
    }
    public static String getServerSocketAddress(ServerSocket serverSocket) {
        return serverSocket.getInetAddress().getHostAddress();
    }

}
