package com.yahia.anotherchatapplicatoin.protocol;

//TODO: replace serverIp with serverName after writing the DNS service
public record ServerStatus(int onlineUsers, String serverIp) {}
