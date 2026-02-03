package com.yahia.chatio.protocol.codec.packet.json;

import com.yahia.chatio.protocol.codec.packet.PacketEncoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.packet.CommunicationPacket;


public class JsonPacketEncoder implements PacketEncoder {
    @Override
    public String encode(CommunicationPacket packet) {
        return JsonHelper.GSON.toJson(packet);
    }
}
