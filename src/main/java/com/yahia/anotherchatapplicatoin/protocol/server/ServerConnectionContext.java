package com.yahia.anotherchatapplicatoin.protocol.server;

import com.yahia.anotherchatapplicatoin.client.Client;

public record ServerConnectionContext(ServerConnection serverConnection, Client client) {}
