package com.yahia.anotherchatapplicatoin.protocol.handshake;

import com.yahia.anotherchatapplicatoin.protocol.ConnectionStatus;

public record HandShakeResponse(ConnectionStatus status) implements HandShake {}
