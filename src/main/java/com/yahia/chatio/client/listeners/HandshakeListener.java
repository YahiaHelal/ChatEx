package com.yahia.chatio.client.listeners;

import com.yahia.chatio.protocol.handshake.ConnectionStatus;

@FunctionalInterface
public interface HandshakeListener {
    void onHandShake(ConnectionStatus status);
}
