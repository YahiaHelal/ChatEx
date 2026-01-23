package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.fin;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadEncoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.terminate.FinPacket;

public class JsonFinEncoder implements PayloadEncoder<FinPacket> {
    @Override
    public String encode(FinPacket payload) {
        return JsonHelper.GSON.toJson(payload, FinPacket.class);
    }
}
