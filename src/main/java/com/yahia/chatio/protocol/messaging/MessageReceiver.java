package com.yahia.chatio.protocol.messaging;

import com.yahia.chatio.protocol.packet.CommunicationPacket;

import java.io.IOException;

public interface MessageReceiver {
    CommunicationPacket receive() throws IOException;
}
