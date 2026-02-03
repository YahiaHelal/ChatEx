package com.yahia.chatio.protocol.codec.packet;
import com.yahia.chatio.protocol.packet.CommunicationPacket;

//NOTE: decodes raw bytes into CommunicationPacket
public interface PacketDecoder {
    CommunicationPacket decode(String raw);
}
