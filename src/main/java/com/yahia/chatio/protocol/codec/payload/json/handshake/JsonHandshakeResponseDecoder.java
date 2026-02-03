package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.handshake.HandshakeResponse;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonHandshakeResponseDecoder implements PayloadDecoder<HandshakeResponse> {
    @Override
    public HandshakeResponse decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, HandshakeResponse.class);
    }
}
