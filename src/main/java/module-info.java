module com.example.guiwithdatabase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.guiwithdatabase to javafx.fxml;
    exports com.example.guiwithdatabase;
}