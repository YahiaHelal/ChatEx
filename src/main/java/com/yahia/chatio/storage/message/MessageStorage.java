package com.yahia.chatio.storage.message;


import com.yahia.chatio.protocol.server.ServerConnection;

import java.util.List;

public class MessageStorage {
    private MessageLoader loader;
    private MessageSaver saver;

    public MessageStorage(MessageLoader loader, MessageSaver saver) {
        this.loader = loader;
        this.saver = saver;
    }


    public List<Message> getServerMessages(ServerConnection serverConnection) {
        return loader.load(serverConnection);
    }

    public void saveMessage(ServerConnection serverConnection, Message message) {
        saver.save(serverConnection, message);
    }
}
