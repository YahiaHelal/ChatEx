package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.fin;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadDecoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.terminate.FinPacket;

public class JsonFinDecoder implements PayloadDecoder<FinPacket> {
    @Override
    public FinPacket decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, FinPacket.class);
    }
}
