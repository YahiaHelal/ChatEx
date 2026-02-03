package com.yahia.chatio.protocol.codec.payload.json.server;

import com.yahia.chatio.protocol.codec.payload.PayloadEncoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.server.ServerConnection;

public class JsonServerInfoEncoder implements PayloadEncoder<ServerConnection> {
    @Override
    public String encode(ServerConnection payload) {
        return JsonHelper.GSON.toJson(payload, ServerConnection.class);
    }
}
