package com.yahia.chatio.protocol.codec.packet.json;

import com.yahia.chatio.protocol.packet.CommunicationPacket;
import com.yahia.chatio.protocol.packet.PacketType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonPacketCodecTest {


    @Test
    void encodesAndDecodesCommunicationPacket() {
        CommunicationPacket packet = new CommunicationPacket(PacketType.BROADCAST_MESSAGE, "test-payload");

        JsonPacketEncoder encoder = new JsonPacketEncoder();
        JsonPacketDecoder decoder = new JsonPacketDecoder();

        String json = encoder.encode(packet);
        CommunicationPacket decoded = decoder.decode(json);

        assertNotNull(json);
        assertEquals(packet.type(), decoded.type());
        assertEquals(packet.payload(), decoded.payload());
    }
}
