package com.yahia.chatio.storage.message;

import com.yahia.chatio.protocol.server.ServerConnection;

import java.util.List;

public interface MessageLoader {
    List<Message> load(ServerConnection serverConnection);
}
