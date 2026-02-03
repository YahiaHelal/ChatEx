package com.yahia.chatio.protocol.codec.payload.json.messaging;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.messaging.BroadCastMessage;

public class JsonBroadcastMessageEncoder implements PayloadEncoder<BroadCastMessage> {
    @Override
    public String encode(BroadCastMessage payload) {
        return JsonHelper.GSON.toJson(payload, BroadCastMessage.class);
    }
}
