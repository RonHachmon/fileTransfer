module com.example.app {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.app to javafx.fxml;
    exports com.example.app;
}