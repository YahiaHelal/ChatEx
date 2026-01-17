package com.yahia.anotherchatapplicatoin.client.listeners;

import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;
import com.yahia.anotherchatapplicatoin.protocol.server.ServerConnectionContext;

@FunctionalInterface
public interface HandshakeListener {
    void onHandShake(ConnectionStatus status);
}
