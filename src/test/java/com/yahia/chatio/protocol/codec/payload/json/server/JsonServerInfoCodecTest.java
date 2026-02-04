package com.yahia.chatio.protocol.codec.payload.json.server;

import com.yahia.chatio.protocol.server.ServerConnection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonServerInfoCodecTest {


    @Test
    void encodesAndDecodesServerInfo() {
        ServerConnection serverConnection = new ServerConnection("192.168.1.1", 9000, "test-server");
        JsonServerInfoEncoder encoder = new JsonServerInfoEncoder();
        JsonServerInfoDecoder decoder = new JsonServerInfoDecoder();

        String json = encoder.encode(serverConnection);

        ServerConnection decoded = decoder.decode(json);

        assertNotNull(json);
        assertEquals(serverConnection.id(), decoded.id());
    }
}
