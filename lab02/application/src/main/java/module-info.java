module pl.edu.pwr.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires library;


    opens pl.edu.pwr.application to javafx.fxml;
    exports pl.edu.pwr.application;
}