package com.yahia.anotherchatapplicatoin.protocol.message;

//TODO: for OCP later
public sealed interface Message
    permits BroadCastMessage, PrivateMessage{}
