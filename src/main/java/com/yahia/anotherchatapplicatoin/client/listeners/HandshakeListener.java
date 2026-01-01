package com.yahia.anotherchatapplicatoin.client.listeners;

import com.yahia.anotherchatapplicatoin.protocol.handshake.ConnectionStatus;

@FunctionalInterface
public interface HandshakeListener {
    void onHandShake(ConnectionStatus status);
}
