package com.yahia.chatio.protocol.codec.payload.json.disconnect;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.disconnect.DisconnectRequest;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonDisconnectRequestEncoder implements PayloadEncoder<DisconnectRequest> {
    @Override
    public String encode(DisconnectRequest payload) {
        return JsonHelper.GSON.toJson(payload, DisconnectRequest.class);
    }
}
