package com.yahia.anotherchatapplicatoin.protocol.codec.payload;

import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;

import java.util.HashMap;

public final class PayloadEncoderRegistry {
    private final HashMap<PacketType, PayloadEncoder<?>> encoders = new HashMap<>();

    public <T> void register(PacketType type, PayloadEncoder<T> encoder) {
        encoders.put(type, encoder);
    }

    @SuppressWarnings("unchecked")
    public <T> PayloadEncoder<T> get(PacketType type) {
        if(encoders.get(type) == null) throw new IllegalStateException(String.format("Packet type %s didn't register an encoder", type));
        return (PayloadEncoder<T>) encoders.get(type);
    }
}
