package com.yahia.anotherchatapplicatoin.transport.tcp;

import com.yahia.anotherchatapplicatoin.protocol.codec.PacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
