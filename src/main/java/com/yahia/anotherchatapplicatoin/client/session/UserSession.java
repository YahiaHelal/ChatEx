package com.yahia.anotherchatapplicatoin.client.session;

import javafx.collections.ObservableList;

public record UserSession(ObservableList<ServerConnection> activeConnections) {}
