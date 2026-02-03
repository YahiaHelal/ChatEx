package com.yahia.chatio.protocol.codec.payload.json.messaging;

import com.yahia.chatio.protocol.messaging.BroadCastMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonBroadCastMessageCodecTest {

    @Test
    void encodesAndDecodesBroadCastMessage() {
        BroadCastMessage message = new BroadCastMessage("test-user", "test-message");

        JsonBroadcastMessageEncoder encoder = new JsonBroadcastMessageEncoder();
        JsonBroadcastMessageDecoder decoder = new JsonBroadcastMessageDecoder();

        String json = encoder.encode(message);
        BroadCastMessage decoded = decoder.decode(json);

        assertNotNull(json);
        assertEquals(message.sender(), decoded.sender());
        assertEquals(message.text(), decoded.text());

    }
}
