package com.yahia.chatio.server.accept;

import com.yahia.chatio.protocol.messaging.MessageReceiver;
import com.yahia.chatio.protocol.messaging.MessageSender;

import java.net.Socket;

public record ServerHandshakeContext(
        MessageSender sender,
        MessageReceiver receiver,
        Socket clientSocket)
{}
