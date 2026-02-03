package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.handshake.HandshakeResponse;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonHandshakeResponseEncoder implements PayloadEncoder<HandshakeResponse> {
    @Override
    public String encode(HandshakeResponse payload) {
        return JsonHelper.GSON.toJson(payload, HandshakeResponse.class);
    }
}
