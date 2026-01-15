module org.example.anotherchatapplicatoin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;
    requires java.management;
    requires com.google.gson;
    requires java.logging;


    opens com.yahia.anotherchatapplicatoin.app to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.app;
    exports com.yahia.anotherchatapplicatoin.client;
    opens com.yahia.anotherchatapplicatoin.client to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.server;
    opens com.yahia.anotherchatapplicatoin.server to javafx.fxml;
    opens com.yahia.anotherchatapplicatoin.utils.ui to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.ui;
    opens com.yahia.anotherchatapplicatoin.client.listeners to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.client.listeners;
    exports com.yahia.anotherchatapplicatoin.utils.logging;
    opens com.yahia.anotherchatapplicatoin.utils.logging to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.network;
    opens com.yahia.anotherchatapplicatoin.utils.network to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.client.exceptions;
    opens com.yahia.anotherchatapplicatoin.client.exceptions;
    exports com.yahia.anotherchatapplicatoin.protocol.packet;
    opens com.yahia.anotherchatapplicatoin.protocol.packet to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.protocol.messaging;
    opens com.yahia.anotherchatapplicatoin.protocol.messaging to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.protocol.disconnect;
    opens com.yahia.anotherchatapplicatoin.protocol.disconnect to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.protocol.server;
    opens com.yahia.anotherchatapplicatoin.protocol.server to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.protocol.handshake;
    opens com.yahia.anotherchatapplicatoin.protocol.handshake to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.protocol.json;
    opens com.yahia.anotherchatapplicatoin.protocol.json to javafx.fxml;
    opens com.yahia.anotherchatapplicatoin.server.session to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.server.session;
    exports com.yahia.anotherchatapplicatoin.client.base;
    opens com.yahia.anotherchatapplicatoin.client.base to javafx.fxml;
}