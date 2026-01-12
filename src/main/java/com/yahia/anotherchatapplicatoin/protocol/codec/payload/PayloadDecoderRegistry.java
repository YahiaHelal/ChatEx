package com.yahia.anotherchatapplicatoin.protocol.codec.payload;

import com.yahia.anotherchatapplicatoin.protocol.packet.PacketType;

import java.util.HashMap;

public final class PayloadDecoderRegistry {
    private final HashMap<PacketType, PayloadDecoder<?>> decoders = new HashMap<>();

    public <T> void register(PacketType type, PayloadDecoder<T> decoder) {
        decoders.put(type, decoder);
    }

    @SuppressWarnings("unchecked")
    public <T> PayloadDecoder<T> get(PacketType type) {
        if(decoders.get(type) == null) throw new IllegalStateException(String.format("Packet type %s didn't register a decoder", type));
        return (PayloadDecoder<T>) decoders.get(type);
    }
}
