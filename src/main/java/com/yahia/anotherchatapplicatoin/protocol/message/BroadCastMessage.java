package com.yahia.anotherchatapplicatoin.protocol.message;

public record BroadCastMessage(String sender, String text) implements Message{}
