package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.disconnect;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadEncoder;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectRequest;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonDisconnectRequestEncoder implements PayloadEncoder<DisconnectRequest> {
    @Override
    public String encode(DisconnectRequest payload) {
        return JsonHelper.GSON.toJson(payload, DisconnectRequest.class);
    }
}
