module org.example.anotherchatapplicatoin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;
    requires java.management;
    requires com.google.gson;
    requires java.logging;


    opens com.yahia.chatio.app to javafx.fxml;
    exports com.yahia.chatio.app;
    exports com.yahia.chatio.client;
    opens com.yahia.chatio.client to javafx.fxml;
    exports com.yahia.chatio.server;
    opens com.yahia.chatio.server to javafx.fxml;
    opens com.yahia.chatio.utils.ui to javafx.fxml;
    exports com.yahia.chatio.utils.ui;
    opens com.yahia.chatio.client.listeners to javafx.fxml;
    exports com.yahia.chatio.client.listeners;
    exports com.yahia.chatio.utils.logging;
    opens com.yahia.chatio.utils.logging to javafx.fxml;
    exports com.yahia.chatio.utils.network;
    opens com.yahia.chatio.utils.network to javafx.fxml;
    exports com.yahia.chatio.client.exceptions;
    opens com.yahia.chatio.client.exceptions;
    exports com.yahia.chatio.protocol.packet;
    opens com.yahia.chatio.protocol.packet to javafx.fxml;
    exports com.yahia.chatio.protocol.messaging;
    opens com.yahia.chatio.protocol.messaging to javafx.fxml;
    exports com.yahia.chatio.protocol.disconnect;
    opens com.yahia.chatio.protocol.disconnect to javafx.fxml;
    exports com.yahia.chatio.protocol.server;
    opens com.yahia.chatio.protocol.server to javafx.fxml;
    exports com.yahia.chatio.protocol.handshake;
    opens com.yahia.chatio.protocol.handshake to javafx.fxml;
    exports com.yahia.chatio.protocol.json;
    opens com.yahia.chatio.protocol.json to javafx.fxml;
    opens com.yahia.chatio.server.session to javafx.fxml;
    exports com.yahia.chatio.server.session;
    exports com.yahia.chatio.client.base;
    opens com.yahia.chatio.client.base to javafx.fxml;
    exports com.yahia.chatio.server.network;
    opens com.yahia.chatio.server.network to javafx.fxml;
    opens com.yahia.chatio.protocol.terminate;
    exports com.yahia.chatio.protocol.terminate;
}