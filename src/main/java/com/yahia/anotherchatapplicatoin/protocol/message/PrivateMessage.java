package com.yahia.anotherchatapplicatoin.protocol.message;

public record PrivateMessage(String sender, String recipient, String text) implements Message{}
