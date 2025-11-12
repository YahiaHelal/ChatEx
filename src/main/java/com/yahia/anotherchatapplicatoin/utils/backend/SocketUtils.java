package com.yahia.anotherchatapplicatoin.utils.backend;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtils {

    public static String getSocketAddress(Socket socket) {
        return socket.getInetAddress().getHostAddress();
    }
    public static String getServerSocketAddress(ServerSocket serverSocket) {
        return serverSocket.getInetAddress().getHostAddress();
    }
}
