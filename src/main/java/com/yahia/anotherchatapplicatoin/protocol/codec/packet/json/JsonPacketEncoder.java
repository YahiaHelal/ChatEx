package com.yahia.anotherchatapplicatoin.protocol.codec.packet.json;

import com.yahia.anotherchatapplicatoin.protocol.codec.packet.PacketEncoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;

import java.util.logging.Logger;


public class JsonPacketEncoder implements PacketEncoder {
    @Override
    public String encode(CommunicationPacket packet) {
        return JsonHelper.GSON.toJson(packet);
    }
}
