package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.handshake;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadDecoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeResponse;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonHandshakeResponseDecoder implements PayloadDecoder<HandshakeResponse> {
    @Override
    public HandshakeResponse decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, HandshakeResponse.class);
    }
}
