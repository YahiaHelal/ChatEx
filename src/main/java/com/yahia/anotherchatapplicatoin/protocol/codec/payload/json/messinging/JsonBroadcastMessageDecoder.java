package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.messinging;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadDecoder;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;
import com.yahia.anotherchatapplicatoin.protocol.messaging.BroadCastMessage;

public class JsonBroadcastMessageDecoder implements PayloadDecoder<BroadCastMessage> {
    @Override
    public BroadCastMessage decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, BroadCastMessage.class);
    }
}
