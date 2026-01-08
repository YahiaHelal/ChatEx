package com.yahia.anotherchatapplicatoin.protocol.codec;

import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.packet.CommunicationPacket;
import com.yahia.anotherchatapplicatoin.utils.logging.LogManager;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JsonPacketEncoder implements PacketEncoder {
    private final Logger LOGGER = LogManager.getLogger();
    @Override
    public String encode(CommunicationPacket packet) {
        return JsonHelper.GSON.toJson(packet);
    }
}
