package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.disconnect;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadDecoder;
import com.yahia.anotherchatapplicatoin.protocol.disconnect.DisconnectRequest;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonDisconnectRequestDecoder implements PayloadDecoder<DisconnectRequest> {
    @Override
    public DisconnectRequest decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, DisconnectRequest.class);
    }
}
