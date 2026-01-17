package com.yahia.anotherchatapplicatoin.protocol.server;

import com.yahia.anotherchatapplicatoin.client.Client;

public record ClientConnection(ServerConnection serverConnection, Client client) {}
