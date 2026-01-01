package com.yahia.anotherchatapplicatoin.protocol.server;

//TODO: replace serverIp with serverName after writing the DNS service
public record ServerStatus(int onlineUsers, String serverIp)  {}
