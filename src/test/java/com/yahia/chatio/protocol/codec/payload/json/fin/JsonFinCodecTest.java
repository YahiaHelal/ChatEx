package com.yahia.chatio.protocol.codec.payload.json.fin;

import com.yahia.chatio.protocol.terminate.FinPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonFinCodecTest {


    @Test
    void encodesAndDecodesFin() {
        FinPacket fin = new FinPacket("test-server");
        JsonFinEncoder encoder = new JsonFinEncoder();
        JsonFinDecoder decoder = new JsonFinDecoder();

        String json = encoder.encode(fin);
        FinPacket decoded =  decoder.decode(json);

        assertNotNull(json);
        assertEquals(fin.serverName(), decoded.serverName());
    }
}
