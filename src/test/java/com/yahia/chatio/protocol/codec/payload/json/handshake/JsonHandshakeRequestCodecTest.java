package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.handshake.HandshakeRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonHandshakeRequestCodecTest {

    @Test
    void encodesAndDecodesHandshakeRequest() {
        HandshakeRequest request = new HandshakeRequest("test-user");
        JsonHandshakeRequestEncoder encoder = new JsonHandshakeRequestEncoder();
        JsonHandshakeRequestDecoder decoder = new JsonHandshakeRequestDecoder();

        String json = encoder.encode(request);
        HandshakeRequest decoded = decoder.decode(json);
        assertNotNull(json);
        assertEquals(request.username(), decoded.username());
    }
}
