module org.example.anotherchatapplicatoin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jshell;
    requires java.logging;


    opens com.yahia.anotherchatapplicatoin to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin;
    exports com.yahia.anotherchatapplicatoin.client;
    opens com.yahia.anotherchatapplicatoin.client to javafx.fxml;
    exports com.yahia.anotherchatapplicatoin.server;
    opens com.yahia.anotherchatapplicatoin.server to javafx.fxml;
}