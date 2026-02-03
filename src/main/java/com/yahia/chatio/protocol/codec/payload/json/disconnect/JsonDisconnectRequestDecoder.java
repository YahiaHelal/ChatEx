package com.yahia.chatio.protocol.codec.payload.json.disconnect;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.disconnect.DisconnectRequest;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonDisconnectRequestDecoder implements PayloadDecoder<DisconnectRequest> {
    @Override
    public DisconnectRequest decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, DisconnectRequest.class);
    }
}
