package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.handshake;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadDecoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeRequest;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonHandshakeRequestDecoder implements PayloadDecoder<HandshakeRequest> {
    @Override
    public HandshakeRequest decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, HandshakeRequest.class);
    }
}
