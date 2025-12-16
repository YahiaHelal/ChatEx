package com.yahia.anotherchatapplicatoin.protocol;


public record BroadCastMessage(String sender, String text) implements Message {}
