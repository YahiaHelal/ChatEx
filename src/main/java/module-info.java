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
    exports com.yahia.anotherchatapplicatoin.managers;
    opens com.yahia.anotherchatapplicatoin.managers to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.handlers;
    opens com.yahia.anotherchatapplicatoin.handlers to javafx.fxml;
    opens com.yahia.anotherchatapplicatoin.utils.ui to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.ui;
    opens com.yahia.anotherchatapplicatoin.utils.backend to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.backend;
    opens com.yahia.anotherchatapplicatoin.client.listeners to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.client.listeners;
    exports com.yahia.anotherchatapplicatoin.protocol;
    opens com.yahia.anotherchatapplicatoin.protocol to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils;
    opens com.yahia.anotherchatapplicatoin.utils to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.logging;
    opens com.yahia.anotherchatapplicatoin.utils.logging to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.utils.network;
    opens com.yahia.anotherchatapplicatoin.utils.network to javafx.fxml;
}