package com.yahia.chatio.protocol.codec.payload.json.handshake;

import com.yahia.chatio.protocol.handshake.ConnectionStatus;
import com.yahia.chatio.protocol.handshake.HandshakeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonHandshakeResponseCodecTest {


    @Test
    void encodeAndDecodeHandshakeResponse() {
        HandshakeResponse response = new HandshakeResponse(ConnectionStatus.ACCEPT);
        JsonHandshakeResponseEncoder encoder = new JsonHandshakeResponseEncoder();
        JsonHandshakeResponseDecoder decoder = new JsonHandshakeResponseDecoder();

        String json = encoder.encode(response);
        HandshakeResponse decodedResponse = decoder.decode(json);
        assertNotNull(json);
        assertEquals(response.status(), decodedResponse.status());
    }
}
