package com.yahia.anotherchatapplicatoin.protocol.codec.payload;

public interface PayloadEncoder <T>{
    String encode(T payload);
}
