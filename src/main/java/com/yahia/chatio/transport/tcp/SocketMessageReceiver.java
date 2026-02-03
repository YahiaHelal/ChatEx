package com.yahia.chatio.transport.tcp;

import com.yahia.chatio.protocol.codec.packet.PacketDecoder;
import com.yahia.chatio.protocol.messaging.MessageReceiver;
import com.yahia.chatio.protocol.packet.CommunicationPacket;

import java.io.BufferedReader;
import java.io.IOException;

public class SocketMessageReceiver implements MessageReceiver {
    private final BufferedReader in;
    private final PacketDecoder decoder;

    public SocketMessageReceiver(BufferedReader in, PacketDecoder decoder) {
        this.in = in;
        this.decoder = decoder;
    }

    @Override
    public CommunicationPacket receive() throws IOException {
        String raw = in.readLine();
        if(raw == null) return null;
        return decoder.decode(raw);
    }
}
