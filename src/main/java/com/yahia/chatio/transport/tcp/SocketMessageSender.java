package com.yahia.chatio.transport.tcp;

import com.yahia.chatio.protocol.codec.packet.PacketEncoder;
import com.yahia.chatio.protocol.messaging.MessageSender;
import com.yahia.chatio.protocol.packet.CommunicationPacket;

import java.io.PrintWriter;

public class SocketMessageSender implements MessageSender {
    private final PrintWriter out;
    private final PacketEncoder encoder;

    public SocketMessageSender(PrintWriter out, PacketEncoder encoder) {
        this.out = out;
        this.encoder = encoder;
    }
    @Override
    public void send(CommunicationPacket packet) {
        out.println(encoder.encode(packet));
    }
}
