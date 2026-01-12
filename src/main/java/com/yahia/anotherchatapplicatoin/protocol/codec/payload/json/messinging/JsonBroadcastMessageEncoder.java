package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.messinging;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadEncoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.BroadCastMessage;

public class JsonBroadcastMessageEncoder implements PayloadEncoder<BroadCastMessage> {
    @Override
    public String encode(BroadCastMessage payload) {
        return JsonHelper.GSON.toJson(payload, BroadCastMessage.class);
    }
}
