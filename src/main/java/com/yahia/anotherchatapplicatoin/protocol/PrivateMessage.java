package com.yahia.anotherchatapplicatoin.protocol;

public record PrivateMessage(String sender, String recipient, String text) implements Message {}
