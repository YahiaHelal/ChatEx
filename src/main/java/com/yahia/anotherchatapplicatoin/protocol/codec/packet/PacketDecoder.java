package com.yahia.anotherchatapplicatoin.protocol.codec.packet;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;

//NOTE: decodes raw bytes into CommunicationPacket
public interface PacketDecoder {
    CommunicationPacket decode(String raw);
}
