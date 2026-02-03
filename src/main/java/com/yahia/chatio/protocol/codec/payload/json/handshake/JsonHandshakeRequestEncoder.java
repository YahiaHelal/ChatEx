package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.handshake.HandshakeRequest;
import com.yahia.chatio.protocol.json.JsonHelper;

public class JsonHandshakeRequestEncoder implements PayloadEncoder<HandshakeRequest> {
    @Override
    public String encode(HandshakeRequest payload) {
        return JsonHelper.GSON.toJson(payload, HandshakeRequest.class);
    }
}
