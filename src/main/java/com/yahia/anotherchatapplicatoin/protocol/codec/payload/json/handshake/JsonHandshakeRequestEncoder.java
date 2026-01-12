package com.yahia.anotherchatapplicatoin.protocol.codec.payload.json.handshake;

import com.yahia.anotherchatapplicatoin.protocol.codec.payload.PayloadEncoder;
import com.yahia.anotherchatapplicatoin.protocol.handshake.HandshakeRequest;
import com.yahia.anotherchatapplicatoin.protocol.json.JsonHelper;

public class JsonHandshakeRequestEncoder implements PayloadEncoder<HandshakeRequest> {
    @Override
    public String encode(HandshakeRequest payload) {
        return JsonHelper.GSON.toJson(payload, HandshakeRequest.class);
    }
}
