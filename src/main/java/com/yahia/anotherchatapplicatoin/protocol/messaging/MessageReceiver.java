package com.yahia.anotherchatapplicatoin.protocol.messaging;

import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;

import java.io.IOException;

public interface MessageReceiver {
    CommunicationPacket receive() throws IOException;
}
