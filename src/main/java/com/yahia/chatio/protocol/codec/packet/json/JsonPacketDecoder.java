package com.yahia.chatio.protocol.codec.packet.json;

import com.yahia.chatio.protocol.codec.packet.PacketDecoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.packet.CommunicationPacket;


public class JsonPacketDecoder implements PacketDecoder {

    @Override
    public CommunicationPacket decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, CommunicationPacket.class);
    }
}
