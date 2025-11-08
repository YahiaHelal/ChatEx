module org.example.anotherchatapplicatoin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;
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
    exports com.yahia.anotherchatapplicatoin.utils;
    opens com.yahia.anotherchatapplicatoin.utils to javafx.fxml;
}