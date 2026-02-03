package com.yahia.chatio.protocol.codec.payload.json.fin;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.terminate.FinPacket;

public class JsonFinEncoder implements PayloadEncoder<FinPacket> {
    @Override
    public String encode(FinPacket payload) {
        return JsonHelper.GSON.toJson(payload, FinPacket.class);
    }
}
