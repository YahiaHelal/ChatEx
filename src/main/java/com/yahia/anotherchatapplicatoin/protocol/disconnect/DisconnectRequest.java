package com.yahia.anotherchatapplicatoin.protocol.disconnect;

//TODO: later add a disconnectionReason parameter for the server to decide it will shutdown or keep running
public record DisconnectRequest(String username) {}
