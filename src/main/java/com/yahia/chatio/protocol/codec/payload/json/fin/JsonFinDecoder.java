package com.yahia.chatio.protocol.codec.payload.json.fin;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.terminate.FinPacket;

public class JsonFinDecoder implements PayloadDecoder<FinPacket>  {
    @Override
    public FinPacket decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, FinPacket.class);
    }
}
