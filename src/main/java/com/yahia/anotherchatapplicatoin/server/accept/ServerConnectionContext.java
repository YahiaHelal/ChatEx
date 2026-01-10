package com.yahia.anotherchatapplicatoin.server.accept;

import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageReceiver;
import com.yahia.anotherchatapplicatoin.protocol.messaging.MessageSender;

import java.net.Socket;

public record ServerConnectionContext(
        MessageSender sender,
        MessageReceiver receiver,
        Socket clientSocket)
{}
