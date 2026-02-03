package com.yahia.chatio.protocol.codec.packet;

import com.yahia.chatio.protocol.packet.CommunicationPacket;


//TODO: introduce BinaryPacketEncoder instead of JSON
//TODO: or ProtoBuffs for high frequency messaging
//NOTE: Encodes CommunicationPackets into raw bytes to send over sockets
public interface PacketEncoder {
    String encode(CommunicationPacket packet);
}
