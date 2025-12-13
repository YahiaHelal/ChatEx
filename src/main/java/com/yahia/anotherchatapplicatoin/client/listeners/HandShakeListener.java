package com.yahia.anotherchatapplicatoin.client.listeners;

import com.yahia.anotherchatapplicatoin.protocol.ConnectionStatus;

public interface HandShakeListener {
    void onHandShake(ConnectionStatus status);
}
