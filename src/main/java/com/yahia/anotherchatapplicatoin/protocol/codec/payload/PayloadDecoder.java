package com.yahia.anotherchatapplicatoin.protocol.codec.payload;

public  interface PayloadDecoder <T> {
    T decode(String raw);
}
