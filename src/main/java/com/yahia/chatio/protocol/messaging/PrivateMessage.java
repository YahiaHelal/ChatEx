package com.yahia.chatio.protocol.messaging;

public record PrivateMessage(String sender, String recipient, String text) {}
