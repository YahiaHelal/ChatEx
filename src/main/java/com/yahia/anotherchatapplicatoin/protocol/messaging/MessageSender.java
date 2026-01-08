package com.yahia.anotherchatapplicatoin.protocol.messaging;

import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;

public interface MessageSender {
    void send(CommunicationPacket packet);
}
