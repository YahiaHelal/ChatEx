package com.yahia.chatio.storage.message;

import com.yahia.chatio.protocol.server.ServerConnection;

public interface MessageSaver {
    void save(ServerConnection serverConnection, Message message);
}
