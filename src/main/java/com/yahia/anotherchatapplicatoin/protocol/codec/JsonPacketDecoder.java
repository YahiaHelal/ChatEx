package com.yahia.anotherchatapplicatoin.protocol.codec;

import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;

import java.util.logging.Logger;


public class JsonPacketDecoder implements PacketDecoder{

    @Override
    public CommunicationPacket decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, CommunicationPacket.class);
    }
}
