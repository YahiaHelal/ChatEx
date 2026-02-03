package com.yahia.chatio.protocol.codec.payload;

public  interface PayloadDecoder <T> {
    T decode(String raw);
}
