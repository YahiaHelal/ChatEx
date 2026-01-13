package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.handshake;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadEncoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeResponse;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonHandshakeResponseEncoder implements PayloadEncoder<HandshakeResponse> {
    @Override
    public String encode(HandshakeResponse payload) {
        return JsonHelper.GSON.toJson(payload, HandshakeResponse.class);
    }
}
