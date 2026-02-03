package com.yahia.chatio.protocol.codec.payload.json.messaging;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.messaging.BroadCastMessage;

public class JsonBroadcastMessageDecoder implements PayloadDecoder<BroadCastMessage> {
    @Override
    public BroadCastMessage decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, BroadCastMessage.class);
    }
}
