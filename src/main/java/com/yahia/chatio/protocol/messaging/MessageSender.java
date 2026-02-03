package com.yahia.chatio.protocol.messaging;

import com.yahia.chatio.protocol.packet.CommunicationPacket;

public interface MessageSender {
    void send(CommunicationPacket packet);
}
