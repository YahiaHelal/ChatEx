package com.yahia.chatio.protocol.codec.payload.json.server;

import com.yahia.chatio.protocol.codec.payload.PayloadDecoder;
import com.yahia.chatio.protocol.json.JsonHelper;
import com.yahia.chatio.protocol.server.ServerConnection;

public class JsonServerInfoDecoder implements PayloadDecoder<ServerConnection> {
    @Override
    public ServerConnection decode(String raw) {
        return JsonHelper.GSON.fromJson(raw, ServerConnection.class);
    }
}
