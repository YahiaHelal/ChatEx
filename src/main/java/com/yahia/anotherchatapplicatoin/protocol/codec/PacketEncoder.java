package com.yahia.anotherchatapplicatoin.protocol.codec;

import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;


//TODO: introduce BinaryPacketEncoder instead of JSON
//TODO: or ProtoBuffs for high frequency messaging
//NOTE: Encodes CommunicationPackets into raw bytes to send over sockets
public interface PacketEncoder {
    String encode(CommunicationPacket packet);
}
