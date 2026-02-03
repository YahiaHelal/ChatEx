package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.handshake.HandshakeRequest;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonHandshakeRequestDecoder implements PayloadDecoder<HandshakeRequest> {
    @Override
    public HandshakeRequest decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, HandshakeRequest.class);
    }
}
