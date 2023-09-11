module com.example.dev_140_1_authentication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.dev_140_1_authentication to javafx.fxml;
    exports com.example.dev_140_1_authentication;
}