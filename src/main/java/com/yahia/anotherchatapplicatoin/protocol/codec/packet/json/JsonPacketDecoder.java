package com.yahia.anotherchatapplicatoin.protocol.codec.packet.json;

import com.yahia.anotherchatapplicatoin.protocol.codec.packet.PacketDecoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;


public class JsonPacketDecoder implements PacketDecoder {

    @Override
    public CommunicationPacket decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, CommunicationPacket.class);
    }
}
