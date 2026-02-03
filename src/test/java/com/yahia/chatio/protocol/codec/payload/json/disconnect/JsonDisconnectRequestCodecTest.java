package com.yahia.chatio.protocol.codec.payload.json.disconnect;

import com.yahia.chatio.protocol.disconnect.DisconnectRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonDisconnectRequestCodecTest {



    @Test
    void encodesAndDecodesDisconnectRequest() {
        DisconnectRequest request = new DisconnectRequest("test-user");
        JsonDisconnectRequestEncoder encoder = new JsonDisconnectRequestEncoder();
        JsonDisconnectRequestDecoder decoder = new JsonDisconnectRequestDecoder();

        String json = encoder.encode(request);
        DisconnectRequest decoded = decoder.decode(json);

        assertNotNull(json);
        assertEquals(request.username(), decoded.username());
    }
}
