package com.yahia.chatio.protocol.codec.payload;

public interface PayloadEncoder <T>{
    String encode(T payload);
}
